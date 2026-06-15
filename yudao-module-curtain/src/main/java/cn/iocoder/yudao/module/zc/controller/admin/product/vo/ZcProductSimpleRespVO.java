package cn.iocoder.yudao.module.zc.controller.admin.product.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Schema(description = "管理后台 - 产品精简 Response VO")
@Data
@Accessors(chain = true)
public class ZcProductSimpleRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    private String name;

    @Schema(description = "版本", example = "6")
    private Long versionId;

    @Schema(description = "进货价", example = "14151")
    private BigDecimal inboundPrice;

    @Schema(description = "一级销售价", example = "25120")
    private BigDecimal onePrice;

    @Schema(description = "供应商", example = "25473")
    private Long supplierId;

}

