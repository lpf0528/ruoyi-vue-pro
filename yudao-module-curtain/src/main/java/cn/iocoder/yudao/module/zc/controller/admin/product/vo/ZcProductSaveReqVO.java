package cn.iocoder.yudao.module.zc.controller.admin.product.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 产品新增/修改 Request VO")
@Data
public class ZcProductSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "27909")
    private Long id;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @NotEmpty(message = "名称不能为空")
    private String name;

    @Schema(description = "版本", requiredMode = Schema.RequiredMode.REQUIRED, example = "6")
    @NotNull(message = "版本不能为空")
    private Long versionId;

    @Schema(description = "进货价", example = "14151")
    @DecimalMin(value = "0", message = "进货价不能小于0")
    private BigDecimal inboundPrice;

    @Schema(description = "规格", example = "27939")
    private Long specId;

    @Schema(description = "一级销售价", example = "25120")
    @DecimalMin(value = "0", message = "一级销售价不能小于0")
    private BigDecimal onePrice;

    @Schema(description = "供应商", example = "25473")
    private Long supplierId;

    @Schema(description = "备注")
    private String note;

}