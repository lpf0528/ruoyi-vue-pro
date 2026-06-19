package cn.iocoder.yudao.module.zc.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 产品批次状态枚举
 */
@Getter
@AllArgsConstructor
public enum ZcProductBatchStatusEnum {

    /** 整匹：未裁剪的完整匹布 */
    WHOLE(1, "整匹"),

    /** 零裁：按零头裁剪的批次 */
    PARTIAL_CUT(0, "零裁"),

    /** 余料：整匹裁剪后剩余的布料 */
    SURPLUS(-1, "余料");

    /** 状态码，对应 zc_product_batch.status */
    private final Integer status;

    /** 中文名称 */
    private final String label;

}
