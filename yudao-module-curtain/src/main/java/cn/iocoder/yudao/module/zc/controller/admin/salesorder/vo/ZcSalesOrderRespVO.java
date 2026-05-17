package cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import cn.idev.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 销售订单 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ZcSalesOrderRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "19855")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "订单号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("订单号")
    private String orderNo;

    @Schema(description = "客户", requiredMode = Schema.RequiredMode.REQUIRED, example = "29746")
    @ExcelProperty("客户")
    private Long customerId;

    @Schema(description = "手机")
    @ExcelProperty("手机")
    private String mobile;

    @Schema(description = "品牌", example = "8302")
    @ExcelProperty("品牌")
    private Long brandId;

    @Schema(description = "下单日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("下单日期")
    private LocalDate orderDate;

    @Schema(description = "物流", example = "27080")
    @ExcelProperty("物流")
    private Long logisticId;

    @Schema(description = "收货人")
    @ExcelProperty("收货人")
    private String receiver;

    @Schema(description = "送货地址", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("送货地址")
    private String deliveryAddress;

    @Schema(description = "运费", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("运费")
    private BigDecimal freight;

    @Schema(description = "订单类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "成品帘、面料单")
    @ExcelProperty(value = "订单类型", converter = DictConvert.class)
    @DictFormat("zc_order_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String types;

    @Schema(description = "优惠金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("优惠金额")
    private BigDecimal discountAmount;

    @Schema(description = "总金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("总金额")
    private BigDecimal totalAmount;

    @Schema(description = "订单金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("订单金额")
    private BigDecimal amount;

    @Schema(description = "已收金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("已收金额")
    private BigDecimal amountReceived;

    @Schema(description = "交付日期")
    @ExcelProperty("交付日期")
    private LocalDate deliveryDate;

    @Schema(description = "结算状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty(value = "结算状态", converter = DictConvert.class)
    @DictFormat("zc_order_pay_status") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String payStatus;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty(value = "状态", converter = DictConvert.class)
    @DictFormat("zc_order_status") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String status;

    @Schema(description = "确认时间")
    @ExcelProperty("确认时间")
    private LocalDateTime confirmTime;

    @Schema(description = "是否加急", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否加急")
    private Boolean isExpedited;

    @Schema(description = "备注")
    @ExcelProperty("备注")
    private String note;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}