package cn.iocoder.yudao.module.zc.controller.admin.productversion.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 产品版本规格精简 Response VO，用于前端下拉选项
 */
@Schema(description = "管理后台 - 产品版本规格精简 Response VO")
@Data
public class ZcProductVersionSpcSimpleRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "版本编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "2048")
    private Long versionId;

    @Schema(description = "版本名称")
    private String versionName;

    @Schema(description = "规格", requiredMode = Schema.RequiredMode.REQUIRED)
    private String spec;

    @Schema(description = "进货价", example = "100")
    private BigDecimal inboundPrice;

    @Schema(description = "一级类销售价", example = "200")
    private BigDecimal onePrice;

    @Schema(description = "备注")
    private String note;

}
