package cn.iocoder.yudao.module.zc.dal.redis;

/**
 * 智仓（ZC）模块 Redis Key 枚举
 *
 * <p>所有 Key 均包含日期段，跨日自动形成新序列；Key 通过过期时间自动清理，无需手动维护。</p>
 *
 * @author 01Coder
 */
public interface ZcRedisKeyConstants {

    /**
     * 销售订单日序号
     *
     * <p>KEY 格式：{@code zc:seq:order:{tenantId}:{yyyyMMdd}}</p>
     * <p>VALUE 类型：String（Redis 原生自增整数）</p>
     * <p>过期时间：48 小时（Key 含日期，跨日即为新 Key，旧 Key 自然失效）</p>
     */
    String ZC_ORDER_SEQ = "zc:seq:order:%d:%s";

    /**
     * 收款单日序号
     *
     * <p>KEY 格式：{@code zc:seq:bill:{tenantId}:{yyyyMMdd}}</p>
     * <p>VALUE 类型：String（Redis 原生自增整数）</p>
     * <p>过期时间：48 小时</p>
     */
    String ZC_BILL_SEQ = "zc:seq:bill:%d:%s";

    /**
     * 产品批次日序号（按产品隔离）
     *
     * <p>KEY 格式：{@code zc:seq:batch:{tenantId}:{productId}:{yyyyMMdd}}</p>
     * <p>VALUE 类型：String（Redis 原生自增整数）</p>
     * <p>过期时间：48 小时</p>
     */
    String ZC_BATCH_SEQ = "zc:seq:batch:%d:%d:%s";

}
