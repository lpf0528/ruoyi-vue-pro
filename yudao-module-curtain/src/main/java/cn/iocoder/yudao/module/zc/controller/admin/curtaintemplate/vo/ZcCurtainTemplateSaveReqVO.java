package cn.iocoder.yudao.module.zc.controller.admin.curtaintemplate.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 窗帘模板新增/修改 Request VO")
@Data
public class ZcCurtainTemplateSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "516")
    private Long id;

    @Schema(description = "款式", requiredMode = Schema.RequiredMode.REQUIRED, example = "9997")
    @NotNull(message = "款式不能为空")
    private Long curtainId;

    @Schema(description = "结构", requiredMode = Schema.RequiredMode.REQUIRED, example = "25411")
    @NotNull(message = "结构不能为空")
    private Long structureId;

    @Schema(description = "配件", requiredMode = Schema.RequiredMode.REQUIRED, example = "32517")
    @NotNull(message = "配件不能为空")
    private Long elementId;

    @Schema(description = "单位", example = "15552")
    private Long unitId;

}