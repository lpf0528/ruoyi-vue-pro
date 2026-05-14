package cn.iocoder.yudao.module.zc.controller.admin.supplier.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import cn.idev.excel.annotation.*;

@Schema(description = "管理后台 - 供应商 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ZcSupplierRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "27125")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "简称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @ExcelProperty("简称")
    private String shortName;

    @Schema(description = "全称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @ExcelProperty("全称")
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