package cn.iocoder.yudao.module.zc.controller.admin.product.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import cn.idev.excel.annotation.*;

@Schema(description = "管理后台 - 产品 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ZcProductRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "27909")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @ExcelProperty("名称")
    private String name;

    @Schema(description = "版本", requiredMode = Schema.RequiredMode.REQUIRED, example = "6")
    @ExcelProperty("版本")
    private Long versionId;

    @Schema(description = "进货价", example = "14151")
    @ExcelProperty("进货价")
    private BigDecimal inboundPrice;

    @Schema(description = "一级销售价", example = "25120")
    @ExcelProperty("一级销售价")
    private BigDecimal onePrice;

    @Schema(description = "供应商", example = "25473")
    @ExcelProperty("供应商")
    private Long supplierId;

    @Schema(description = "备注")
    @ExcelProperty("备注")
    private String note;

    @Schema(description = "版本名称")
    @ExcelProperty("版本名称")
    private String versionName;

    @Schema(description = "单位")
    @ExcelProperty("单位")
    private String unitValue;

    @Schema(description = "供应商名称")
    @ExcelProperty("供应商名称")
    private String supplierName;

    @Schema(description = "创建人名称")
    @ExcelProperty("创建人名称")
    private String creatorName;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}