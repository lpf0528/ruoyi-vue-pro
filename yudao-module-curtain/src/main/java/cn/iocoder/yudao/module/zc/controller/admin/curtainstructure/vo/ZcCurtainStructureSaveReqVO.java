package cn.iocoder.yudao.module.zc.controller.admin.curtainstructure.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 窗帘结构新增/修改 Request VO")
@Data
public class ZcCurtainStructureSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "10163")
    private Long id;

    @Schema(description = "结构名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @NotEmpty(message = "结构名称不能为空")
    private String name;

    @Schema(description = "结构类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotEmpty(message = "结构类型不能为空")
    private String type;

    @Schema(description = "备注")
    private String note;

}