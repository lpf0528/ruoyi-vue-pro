package cn.iocoder.yudao.module.zc.controller.admin.bills.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.*;
import java.math.BigDecimal;
import cn.idev.excel.annotation.*;

@Schema(description = "管理后台 - 收支账单 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ZcBillsRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "5800")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "单号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("单号")
    private String billNo;

    @Schema(description = "付款时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("付款时间")
    private LocalDate billDate;

    @Schema(description = "财务人员", requiredMode = Schema.RequiredMode.REQUIRED, example = "25823")
    @ExcelProperty("财务人员")
    private Long billUserId;

    @Schema(description = "客户", requiredMode = Schema.RequiredMode.REQUIRED, example = "11545")
    @ExcelProperty("客户")
    private Long customerId;

    @Schema(description = "优惠金额")
    @ExcelProperty("优惠金额")
    private BigDecimal discountAmount;

    @Schema(description = "实收金额 ", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("实收金额 ")
    private BigDecimal actualAmount;

    @Schema(description = "收支方式", requiredMode = Schema.RequiredMode.REQUIRED, example = "19006")
    @ExcelProperty("收支方式")
    private Long billMethodId;

    @Schema(description = "备注")
    @ExcelProperty("备注")
    private String note;

    /** 客户简称，JOIN zc_customer.short_name */
    @Schema(description = "客户简称")
    private String customerName;

    /** 收款方式名称，JOIN zc_bill_methods.name */
    @Schema(description = "收款方式名称")
    private String billMethodName;

}