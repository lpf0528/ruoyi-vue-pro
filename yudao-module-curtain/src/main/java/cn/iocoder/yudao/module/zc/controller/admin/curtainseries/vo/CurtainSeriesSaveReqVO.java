package cn.iocoder.yudao.module.zc.controller.admin.curtainseries.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 窗帘系列新增/修改 Request VO")
@Data
public class CurtainSeriesSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "1684")
    private Long id;

    @Schema(description = "系列名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @NotEmpty(message = "系列名称不能为空")
    private String name;

    @Schema(description = "0窗帘 1软装 2罗马帘 3百叶帘", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "0窗帘 1软装 2罗马帘 3百叶帘不能为空")
    private Integer category;

    @Schema(description = "备注")
    private String note;

}