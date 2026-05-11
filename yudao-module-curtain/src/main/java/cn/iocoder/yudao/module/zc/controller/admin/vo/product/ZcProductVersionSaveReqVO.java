package cn.iocoder.yudao.module.zc.controller.admin.vo.product;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ZcProductVersionSaveReqVO {

    private Long id;
    private String name;
    private String unitValue;
    private String specValue;
    private String categoryValue;
    private String sellingPriceType;
    private BigDecimal inboundPrice;
    private Integer bizType;
    private Long supplierId;
    private String note;

}
