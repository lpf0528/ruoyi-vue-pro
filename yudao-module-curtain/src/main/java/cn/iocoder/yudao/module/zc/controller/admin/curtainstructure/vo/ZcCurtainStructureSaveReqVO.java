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

    @Schema(description = "属性多选：长、宽、高、等")
    private List<String> attributes;

    @Schema(description = "备注")
    private String note;

}