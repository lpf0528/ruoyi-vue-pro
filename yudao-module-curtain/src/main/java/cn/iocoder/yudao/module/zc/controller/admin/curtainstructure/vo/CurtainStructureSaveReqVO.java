package cn.iocoder.yudao.module.zc.controller.admin.curtainstructure.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 窗帘结构部位新增/修改 Request VO")
@Data
public class CurtainStructureSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "10163")
    private Long id;

    @Schema(description = "部位名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @NotEmpty(message = "部位名称不能为空")
    private String name;

    @Schema(description = "帘头/帘身/飘窗垫/其他", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotEmpty(message = "帘头/帘身/飘窗垫/其他不能为空")
    private String type;

    @Schema(description = "备注")
    private String note;

}