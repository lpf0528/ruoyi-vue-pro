package cn.iocoder.yudao.module.zc.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 成品订单-用料明细 配料状态枚举
 */
@Getter
@AllArgsConstructor
public enum ZcSalesOrderMaterialStatusEnum {

    /** 未配料：尚未绑定批次和裁剪数量 */
    NOT_PEILIAO("未配料"),

    /** 已配料：已完成裁剪出库，批次库存已扣减 */
    HAVE_PEILIAO("已配料");

    /** 中文名称 */
    private final String label;

}
