package cn.iocoder.yudao.module.zc.controller.admin.productversion.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

@Schema(description = "管理后台 - 产品版本精简 Response VO")
@Data
@Accessors(chain = true)
public class ZcProductVersionSimpleRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "版本名称")
    private String name;

    @Schema(description = "单位")
    private String unitValue;

    @Schema(description = "类别ID")
    private Long categoryId;

    @Schema(description = "出货价类型")
    private String sellingPriceType;

    @Schema(description = "进货价")
    private BigDecimal inboundPrice;

    @Schema(description = "一级类销售价")
    private BigDecimal onePrice;

    @Schema(description = "供应商ID")
    private Long supplierId;

    @Schema(description = "规格信息列表")
    private List<ZcProductVersionSpcRespVO> specConfs;

}
