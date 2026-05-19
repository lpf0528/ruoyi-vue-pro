package cn.iocoder.yudao.module.zc.controller.admin.productbatch.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.idev.excel.annotation.*;

@Schema(description = "管理后台 - 产品批次 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ZcProductBatchRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "209")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "批号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("批号")
    private String batchNo;

    @Schema(description = "入库日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("入库日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String inboundDate;

    @Schema(description = "产品", requiredMode = Schema.RequiredMode.REQUIRED, example = "7855")
    @ExcelProperty("产品")
    private Long productId;

    @Schema(description = "进货价", example = "6838")
    @ExcelProperty("进货价")
    private BigDecimal inboundPrice;

    @Schema(description = "入库数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("入库数量")
    private BigDecimal inboundQuantity;

    @Schema(description = "剩余数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("剩余数量")
    private BigDecimal quantity;

    @Schema(description = "仓库", example = "5470")
    @ExcelProperty("仓库")
    private Long warehouseId;

    @Schema(description = "供应商", example = "12241")
    @ExcelProperty("供应商")
    private Long supplierId;

    @Schema(description = "备注")
    @ExcelProperty("备注")
    private String note;

    @Schema(description = "产品名称")
    @ExcelProperty("产品名称")
    private String productName;

    @Schema(description = "产品价格")
    @ExcelProperty("产品价格")
    private BigDecimal productPrice;

    @Schema(description = "规格")
    @ExcelProperty("规格")
    private String specValue;

    @Schema(description = "版本名称")
    @ExcelProperty("版本名称")
    private String versionName;

    @Schema(description = "供应商名称")
    @ExcelProperty("供应商名称")
    private String supplierName;

    @Schema(description = "仓库名称")
    @ExcelProperty("仓库名称")
    private String warehouseName;

    @Schema(description = "单位（来自产品版本）")
    @ExcelProperty("单位")
    private String unitValue;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}