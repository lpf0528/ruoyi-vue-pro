package cn.iocoder.yudao.module.zc.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 销售订单支付状态枚举
 *
 * <p>对应字典类型 {@code zc_order_pay_status}</p>
 */
@Getter
@AllArgsConstructor
public enum ZcSalesOrderPayStatusEnum {

    /** 未支付：订单创建后默认状态，尚未收到任何款项 */
    UNPAID("未支付"),

    /** 部分支付：已收到部分款项，但未达到订单金额 */
    PARTIALPAID("部分支付"),

    /** 已支付：收款金额已达到或超过订单金额 */
    PAID("已支付");

    /** 中文名称 */
    private final String label;

}
