package cn.iocoder.yudao.module.zc.controller.admin.productbatch.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import cn.idev.excel.annotation.*;

@Schema(description = "管理后台 - 产品批次 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ZcProductBatchRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "31107")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "批号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("批号")
    private String batchNo;

    @Schema(description = "入库日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("入库日期")
    private LocalDate inboundDate;

    @Schema(description = "产品", requiredMode = Schema.RequiredMode.REQUIRED, example = "5889")
    @ExcelProperty("产品")
    private Long productId;

    @Schema(description = "入库数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("入库数量")
    private BigDecimal inboundQuantity;

    @Schema(description = "剩余数量")
    @ExcelProperty("剩余数量")
    private BigDecimal quantity;

    @Schema(description = "仓库", example = "17396")
    @ExcelProperty("仓库")
    private Long warehouseId;

    @Schema(description = "供应商", example = "28315")
    @ExcelProperty("供应商")
    private Long supplierId;

    @Schema(description = "备注")
    @ExcelProperty("备注")
    private String note;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}