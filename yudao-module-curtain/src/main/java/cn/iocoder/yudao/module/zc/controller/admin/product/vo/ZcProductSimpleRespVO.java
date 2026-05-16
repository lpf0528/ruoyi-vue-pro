package cn.iocoder.yudao.module.zc.controller.admin.product.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 货号档案精简 Response VO")
@Data
@Accessors(chain = true)
public class ZcProductSimpleRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "产品名称")
    private String name;

    @Schema(description = "版本")
    private Long versionId;

    @Schema(description = "供应商")
    private Long supplierId;

    @Schema(description = "A 类销售价")
    private BigDecimal aPrice;

    @Schema(description = "进货价")
    private BigDecimal inboundPrice;

}
