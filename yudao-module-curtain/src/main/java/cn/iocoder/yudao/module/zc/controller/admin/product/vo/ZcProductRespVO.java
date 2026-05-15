package cn.iocoder.yudao.module.zc.controller.admin.product.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import cn.idev.excel.annotation.*;

@Schema(description = "管理后台 - 货号档案 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ZcProductRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "21159")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "产品名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @ExcelProperty("产品名称")
    private String name;

    @Schema(description = "版本", example = "17507")
    @ExcelProperty("版本")
    private Long versionId;

    @Schema(description = "进货价", example = "14151")
    @ExcelProperty("进货价")
    private BigDecimal inboundPrice;

    @Schema(description = "A 类销售价", example = "12540")
    @ExcelProperty("A 类销售价")
    private BigDecimal aPrice;

    @Schema(description = "供应商", example = "25473")
    @ExcelProperty("供应商")
    private Long supplierId;

    @Schema(description = "采购类型", example = "0 整采 1 零采")
    @ExcelProperty("采购类型")
    private Integer purchaseType;

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