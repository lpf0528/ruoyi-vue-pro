package cn.iocoder.yudao.module.zc.enums;

import lombok.Getter;

/**
 * 销售订单类型枚举
 *
 * <p>对应字典类型 {@code zc_order_type}</p>
 */
@Getter
public enum ZcOrderTypeEnum {

    /** 面料单：直接购买产品批次，无工艺配置 */
    FABRIC("面料单"),

    /** 成品单：包含窗帘工艺配置的完整订单 */
    CURTAIN("成品单");

    /** 中文名称 */
    private final String label;

    ZcOrderTypeEnum(String label) {
        this.label = label;
    }

}
