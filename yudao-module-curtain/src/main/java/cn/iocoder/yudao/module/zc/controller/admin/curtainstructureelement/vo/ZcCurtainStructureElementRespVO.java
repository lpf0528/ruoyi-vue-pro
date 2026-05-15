package cn.iocoder.yudao.module.zc.controller.admin.curtainstructureelement.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import cn.idev.excel.annotation.*;

@Schema(description = "管理后台 - 窗帘结构组件 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ZcCurtainStructureElementRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "15929")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "组件名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ExcelProperty("组件名称")
    private String name;

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