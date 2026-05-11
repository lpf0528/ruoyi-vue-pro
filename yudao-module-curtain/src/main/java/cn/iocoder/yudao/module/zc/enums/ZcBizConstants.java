package cn.iocoder.yudao.module.zc.enums;

/**
 * 智仓业务常量（与 zc_sales_order 等 varchar 字段存储值一致）
 */
public final class ZcBizConstants {

    private ZcBizConstants() {}

    /** 订单类型：成品帘 */
    public static final String ORDER_TYPE_CURTAIN = "curtain";
    /** 订单类型：面料单 */
    public static final String ORDER_TYPE_FABRIC = "fabric";

    /** 结算状态 */
    public static final String PAY_STATUS_UNPAID = "unpaid";
    public static final String PAY_STATUS_PARTIAL = "partial";
    public static final String PAY_STATUS_PAID = "paid";

    /** 确认状态 */
    public static final String CONFIRM_UNCONFIRMED = "unconfirmed";
    public static final String CONFIRM_CONFIRMED = "confirmed";

    /** 余额流水 biz_type */
    public static final String BIZ_ORDER_CONFIRM = "ORDER_CONFIRM";
    public static final String BIZ_ORDER_UNCONFIRM = "ORDER_UNCONFIRM";
    /** 收款入账（余额流水 biz_type） */
    public static final String BIZ_COLLECTION = "COLLECTION";
    public static final String BIZ_MANUAL_ADJUST = "MANUAL_ADJUST";

    public static final String REF_SALES_ORDER = "SALES_ORDER";
    public static final String REF_COLLECTION = "COLLECTION_RECORD";

    /** 生产队列 queue_status：待处理 */
    public static final int QUEUE_PENDING = 0;
    /** 生产中 */
    public static final int QUEUE_DOING = 1;
    /** 已完成 */
    public static final int QUEUE_DONE = 2;

}
