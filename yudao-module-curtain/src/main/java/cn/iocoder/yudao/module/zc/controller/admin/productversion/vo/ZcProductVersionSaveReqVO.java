package cn.iocoder.yudao.module.zc.controller.admin.productversion.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 产品版本新增/修改 Request VO")
@Data
public class ZcProductVersionSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "22953")
    private Long id;

    @Schema(description = "版本名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @NotEmpty(message = "版本名称不能为空")
    private String name;

    @Schema(description = "单位（字典）")
    private String unitValue;

    @Schema(description = "规格ID", example = "14205")
    private Long specId;

    @Schema(description = "规格值")
    private String specValue;

    @Schema(description = "类别ID", example = "12697")
    private Long categoryId;

    @Schema(description = "物料类别")
    private String categoryValue;

    @Schema(description = "出货价类型", example = "fixed_price:统一价、sku_price:型号价")
    private String sellingPriceType;

    @Schema(description = "进货价", example = "13750")
    private BigDecimal inboundPrice;

    @Schema(description = "分类")
    private Integer classify;

    @Schema(description = "供应商", example = "21214")
    private Long supplierId;

    @Schema(description = "备注")
    private String note;

}