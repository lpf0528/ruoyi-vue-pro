package cn.iocoder.yudao.module.zc.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 余额变动关联单据类型枚举
 *
 * <p>用于 {@code zc_customer_balance_log.ref_type} 字段，标识触发余额变动的来源单据类型。</p>
 */
@Getter
@AllArgsConstructor
public enum ZcRefTypeEnum {

    /** 销售单 */
    SALES_ORDER("销售单"),

    /** 收款单 */
    COLLECTION_RECORD("收款单");

    private final String label;

}
