package cn.iocoder.yudao.module.zc.controller.admin.supplier.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Schema(description = "管理后台 - 供应商精简 Response VO")
@Data
@Accessors(chain = true)
public class ZcSupplierSimpleRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "简称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String shortName;

}
