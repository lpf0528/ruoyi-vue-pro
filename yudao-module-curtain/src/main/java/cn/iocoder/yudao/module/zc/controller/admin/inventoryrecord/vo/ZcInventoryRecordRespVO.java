package cn.iocoder.yudao.module.zc.controller.admin.inventoryrecord.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import cn.idev.excel.annotation.*;

@Schema(description = "管理后台 - 盘点记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ZcInventoryRecordRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "8627")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "产品", requiredMode = Schema.RequiredMode.REQUIRED, example = "9127")
    @ExcelProperty("产品")
    private Long productId;

    @Schema(description = "批次", requiredMode = Schema.RequiredMode.REQUIRED, example = "8051")
    @ExcelProperty("批次")
    private Long batchId;

    @Schema(description = "盘点前数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("盘点前数量")
    private BigDecimal oldQuantity;

    @Schema(description = "盘点后数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("盘点后数量")
    private BigDecimal newQuantity;

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