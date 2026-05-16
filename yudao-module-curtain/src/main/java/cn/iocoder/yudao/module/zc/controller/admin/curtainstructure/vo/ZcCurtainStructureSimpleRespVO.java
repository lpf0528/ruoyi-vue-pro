package cn.iocoder.yudao.module.zc.controller.admin.curtainstructure.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Schema(description = "管理后台 - 窗帘结构精简 Response VO")
@Data
@Accessors(chain = true)
public class ZcCurtainStructureSimpleRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "结构名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "结构类型", requiredMode = Schema.RequiredMode.REQUIRED)
    private String type;

}
