package cn.iocoder.yudao.module.quiz.controller.admin.projectcategory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import cn.idev.excel.annotation.*;

@Schema(description = "管理后台 - 项目分类 Response VO")
@Data
@ExcelIgnoreUnannotated
public class QuizQuizProjectCategoryRespVO {

    @Schema(description = "项目分类编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "26896")
    @ExcelProperty("项目分类编号")
    private Long id;

    @Schema(description = "分类名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @ExcelProperty("分类名称")
    private String name;

    @Schema(description = "图标地址", example = "https://www.iocoder.cn")
    @ExcelProperty("图标地址")
    private String picUrl;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("状态")
    private Integer status;

    @Schema(description = "排序")
    @ExcelProperty("排序")
    private Integer sort;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}