package cn.iocoder.yudao.module.zc.dal.redis;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
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
        return increment(String.format(ZC_BILL_SEQ, tenantId, date));
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
     * 对指定 Key 执行 Redis INCR，并每次刷新 48 小时 TTL
     *
     * <p>TTL 在每次调用时重置，保证当天持续有创建请求时 Key 不会意外过期。
     * 超过 48 小时无新增请求后，Key 自动清理。</p>
     */
    private long increment(String key) {
        Long seq = stringRedisTemplate.opsForValue().increment(key);
        // 每次调用刷新过期时间，防止当日首次调用后 Key 过早过期
        stringRedisTemplate.expire(key, EXPIRE_HOURS, TimeUnit.HOURS);
        return seq != null ? seq : 1L;
    }

}
