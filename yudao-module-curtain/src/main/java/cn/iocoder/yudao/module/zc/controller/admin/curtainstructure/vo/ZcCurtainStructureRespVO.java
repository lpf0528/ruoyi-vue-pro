package cn.iocoder.yudao.module.zc.controller.admin.curtainstructure.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import cn.idev.excel.annotation.*;

@Schema(description = "管理后台 - 窗帘结构 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ZcCurtainStructureRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "10163")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "结构名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @ExcelProperty("结构名称")
    private String name;

    @Schema(description = "属性多选：长、宽、高、等")
    private List<String> attributes;

    @Schema(description = "备注")
    @ExcelProperty("备注")
    private String note;

    @Schema(description = "创建者")
    @ExcelProperty("创建者")
    private String creator;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}