package cn.iocoder.yudao.module.zc.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 销售订单状态枚举
 *
 * <p>对应字典类型 {@code zc_order_status}</p>
 */
@Getter
@AllArgsConstructor
public enum ZcSalesOrderStatusEnum {

    /** 未确认：订单刚创建，尚未审核 */
    UNCONFIRMED("未确认"),

    /** 已确认：订单已审核确认，进入生产流程 */
    CONFIRMED("已确认"),

    /** 已打包：生产完成，已完成打包备货 */
    DABAO("已打包"),

    /** 已发货：货物已发出，等待客户签收 */
    FAHUO("已发货");

    /** 中文名称 */
    private final String label;

}
