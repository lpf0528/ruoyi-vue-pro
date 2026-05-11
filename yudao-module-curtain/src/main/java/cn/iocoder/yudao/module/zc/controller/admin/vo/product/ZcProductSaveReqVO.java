package cn.iocoder.yudao.module.zc.controller.admin.vo.product;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ZcProductSaveReqVO {

    private Long id;
    private String name;
    private Long versionId;
    private BigDecimal inboundPrice;
    private BigDecimal aPrice;
    private BigDecimal bPrice;
    private Long supplierId;
    private Integer purchaseType;
    private String note;

}
