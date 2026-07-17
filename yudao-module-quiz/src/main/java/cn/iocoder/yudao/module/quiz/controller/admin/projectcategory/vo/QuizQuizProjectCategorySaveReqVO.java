package cn.iocoder.yudao.module.quiz.controller.admin.projectcategory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 项目分类新增/修改 Request VO")
@Data
public class QuizQuizProjectCategorySaveReqVO {

    @Schema(description = "项目分类编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "26896")
    private Long id;

    @Schema(description = "分类名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @NotEmpty(message = "分类名称不能为空")
    private String name;

    @Schema(description = "图标地址", example = "https://www.iocoder.cn")
    private String picUrl;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "状态不能为空")
    private Integer status;

    @Schema(description = "排序")
    private Integer sort;

}