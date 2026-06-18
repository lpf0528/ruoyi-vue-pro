package cn.iocoder.yudao.module.zc.dal.redis;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.function.Supplier;
import java.util.concurrent.TimeUnit;

import static cn.iocoder.yudao.module.zc.dal.redis.ZcRedisKeyConstants.*;

/**
 * 智仓（ZC）单号生成 Redis DAO
 *
 * <p>使用 Redis INCR 命令保证并发安全的自增序号，替代 {@code COUNT(*)+1} 方案。
 * 每个 Key 内嵌日期，跨日自动形成新序列；Key 设置 48 小时过期，自动清理。</p>
 *
 * @author 01Coder
 */
@Repository
public class ZcNoGeneratorRedisDAO {

    /** Redis Key 过期时间：48 小时，确保跨日后旧 Key 能自动清理 */
    private static final long EXPIRE_HOURS = 48;

    /**
     * 原子：将 Redis 序号与库内最大序号对齐后 INCR
     *
     * <p>ARGV[1] 为库内最大序号；若 Redis 当前值小于库内最大值则先提升到 dbMax，再 INCR。</p>
     */
    private static final DefaultRedisScript<Long> INCR_WITH_DB_SYNC_SCRIPT;

    static {
        INCR_WITH_DB_SYNC_SCRIPT = new DefaultRedisScript<>();
        INCR_WITH_DB_SYNC_SCRIPT.setResultType(Long.class);
        INCR_WITH_DB_SYNC_SCRIPT.setScriptText(
                "local dbMax = tonumber(ARGV[1])\n" +
                "if dbMax and dbMax > 0 then\n" +
                "  local cur = tonumber(redis.call('GET', KEYS[1]) or '0')\n" +
                "  if cur < dbMax then\n" +
                "    redis.call('SET', KEYS[1], dbMax)\n" +
                "  end\n" +
                "end\n" +
                "return redis.call('INCR', KEYS[1])");
    }

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 获取当日销售订单序号（原子自增，从 1 开始）
     *
     * @param tenantId 租户 ID（不同租户序号独立）
     * @param date     日期字符串，格式 yyyyMMdd
     * @return 当日序号，从 1 开始单调递增
     */
    public long nextOrderSeq(long tenantId, String date) {
        return increment(String.format(ZC_ORDER_SEQ, tenantId, date));
    }

    /**
     * 获取当日收款单序号（原子自增，从 1 开始）
     *
     * @param tenantId 租户 ID（不同租户序号独立）
     * @param date     日期字符串，格式 yyyyMMdd
     * @return 当日序号，从 1 开始单调递增
     */
    public long nextBillSeq(long tenantId, String date) {
        return nextBillSeq(tenantId, date, null);
    }

    /**
     * 获取当日收款单序号，每次与库内最大序号对齐后再 INCR，避免 Redis 重置/落后导致单号重复
     *
     * @param dbMaxSeqSupplier 查询库内当日最大序号的回调，可为 null
     */
    public long nextBillSeq(long tenantId, String date, Supplier<Long> dbMaxSeqSupplier) {
        return incrementWithDbSync(String.format(ZC_BILL_SEQ, tenantId, date), dbMaxSeqSupplier);
    }

    /**
     * 获取当日产品批次序号（原子自增，从 1 开始，按产品隔离）
     *
     * @param tenantId  租户 ID
     * @param productId 产品 ID（不同产品序号独立）
     * @param date      日期字符串，格式 yyyyMMdd
     * @return 当日序号，从 1 开始单调递增
     */
    public long nextBatchSeq(long tenantId, long productId, String date) {
        return increment(String.format(ZC_BATCH_SEQ, tenantId, productId, date));
    }

    /**
     * 与库内最大序号对齐后执行 INCR（Lua 脚本保证原子性）
     */
    private long incrementWithDbSync(String key, Supplier<Long> dbMaxSeqSupplier) {
        long dbMaxVal = 0L;
        if (dbMaxSeqSupplier != null) {
            Long dbMax = dbMaxSeqSupplier.get();
            if (dbMax != null && dbMax > 0) {
                dbMaxVal = dbMax;
            }
        }
        Long seq = stringRedisTemplate.execute(INCR_WITH_DB_SYNC_SCRIPT,
                Collections.singletonList(key), String.valueOf(dbMaxVal));
        stringRedisTemplate.expire(key, EXPIRE_HOURS, TimeUnit.HOURS);
        return seq != null ? seq : 1L;
    }

    /**
     * 对指定 Key 执行 Redis INCR，并每次刷新 48 小时 TTL
     */
    private long increment(String key) {
        Long seq = stringRedisTemplate.opsForValue().increment(key);
        stringRedisTemplate.expire(key, EXPIRE_HOURS, TimeUnit.HOURS);
        return seq != null ? seq : 1L;
    }

}
