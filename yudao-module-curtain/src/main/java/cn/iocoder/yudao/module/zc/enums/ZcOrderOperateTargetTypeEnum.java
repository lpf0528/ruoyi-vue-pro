package cn.iocoder.yudao.module.zc.enums;

import lombok.Getter;

/**
 * 销售订单操作对象类型枚举
 *
 * <p>标识操作发生在哪个层级，对应 {@code zc_order_operation_log.target_type} 字段</p>
 */
@Getter
public enum ZcOrderOperateTargetTypeEnum {

    /** 订单级操作，如确认、取消确认、标记加急 */
    ORDER("订单"),
    /** 窗帘行级操作，如打包、发货 */
    CURTAIN("窗帘行"),
    /** 用料明细级操作，如裁剪配料、撤销裁剪 */
    MATERIAL("用料明细");

    /** 中文名称 */
    private final String label;

    ZcOrderOperateTargetTypeEnum(String label) {
        this.label = label;
    }

}
