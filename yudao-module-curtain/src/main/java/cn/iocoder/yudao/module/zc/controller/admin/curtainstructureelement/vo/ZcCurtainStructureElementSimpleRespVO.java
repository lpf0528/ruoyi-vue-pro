package cn.iocoder.yudao.module.zc.controller.admin.curtainstructureelement.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Schema(description = "管理后台 - 窗帘结构组件精简 Response VO")
@Data
@Accessors(chain = true)
public class ZcCurtainStructureElementSimpleRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "组件名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "是否打印")
    private Boolean isPrint;

    @Schema(description = "计算用料")
    private Boolean isCalMaterial;

}
