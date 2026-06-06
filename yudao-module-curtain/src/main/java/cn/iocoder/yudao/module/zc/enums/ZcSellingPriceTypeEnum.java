package cn.iocoder.yudao.module.zc.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 产品版本出货价类型枚举
 *
 * <p>对应字典类型 {@code zc_selling_price_type}</p>
 */
@Getter
@AllArgsConstructor
public enum ZcSellingPriceTypeEnum {

    /** 统一价：所有客户同一售价 */
    FIXED_PRICE("统一价"),

    /** 型号价：按 SKU 规格单独定价 */
    SKU_PRICE("型号价");

    /** 中文名称 */
    private final String label;

}
