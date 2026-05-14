package cn.iocoder.yudao.module.zc.controller.admin.productspec.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import cn.idev.excel.annotation.*;

@Schema(description = "管理后台 - 产品规格 Response VO")
@Data
@ExcelIgnoreUnannotated
public class zcProductSpecRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "20347")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "规格值", requiredMode = Schema.RequiredMode.REQUIRED, example = "2.5")
    @ExcelProperty("规格值")
    private String value;

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