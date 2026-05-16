package cn.iocoder.yudao.module.zc.controller.admin.productversion.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Schema(description = "管理后台 - 产品版本精简 Response VO")
@Data
@Accessors(chain = true)
public class ZcProductVersionSimpleRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "版本名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "规格ID")
    private Long specId;

    @Schema(description = "类别ID")
    private Long categoryId;

    @Schema(description = "供应商")
    private Long supplierId;

    @Schema(description = "分类")
    private Integer classify;

}
