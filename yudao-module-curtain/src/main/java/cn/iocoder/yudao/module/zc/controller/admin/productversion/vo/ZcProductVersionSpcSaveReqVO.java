package cn.iocoder.yudao.module.zc.controller.admin.productversion.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.math.BigDecimal;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 产品版本规格新增/修改 Request VO")
@Data
public class ZcProductVersionSpcSaveReqVO {

    @Schema(description = "主键", example = "1024")
    private Long id;

    @Schema(description = "版本", requiredMode = Schema.RequiredMode.REQUIRED, example = "2048")
    @NotNull(message = "版本不能为空")
    private Long versionId;

    @Schema(description = "规格", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "规格不能为空")
    private String spec;

    @Schema(description = "进货价", example = "100")
    @DecimalMin(value = "0", message = "进货价不能小于0")
    private BigDecimal inboundPrice;

    @Schema(description = "一级类销售价", example = "200")
    @DecimalMin(value = "0", message = "一级销售价不能小于0")
    private BigDecimal onePrice;

    @Schema(description = "备注")
    private String note;

}
