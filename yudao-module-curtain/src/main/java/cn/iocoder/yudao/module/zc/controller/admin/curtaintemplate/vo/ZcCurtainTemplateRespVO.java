package cn.iocoder.yudao.module.zc.controller.admin.curtaintemplate.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import cn.idev.excel.annotation.*;

@Schema(description = "管理后台 - 窗帘模板 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ZcCurtainTemplateRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "516")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "款式", requiredMode = Schema.RequiredMode.REQUIRED, example = "9997")
    @ExcelProperty("款式")
    private Long curtainId;

    @Schema(description = "结构", requiredMode = Schema.RequiredMode.REQUIRED, example = "25411")
    @ExcelProperty("结构")
    private Long structureId;

    @Schema(description = "配件", requiredMode = Schema.RequiredMode.REQUIRED, example = "32517")
    @ExcelProperty("配件")
    private Long elementId;

    @Schema(description = "款式名称")
    @ExcelProperty("款式名称")
    private String curtainName;

    @Schema(description = "结构名称")
    @ExcelProperty("结构名称")
    private String structureName;

    @Schema(description = "配件名称")
    @ExcelProperty("配件名称")
    private String elementName;

    @Schema(description = "创建者")
    @ExcelProperty("创建者")
    private String creator;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}