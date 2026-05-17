package cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 销售订单新增/修改 Request VO")
@Data
public class ZcSalesOrderSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "19855")
    private Long id;

    @Schema(description = "订单号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "订单号不能为空")
    private String orderNo;

    @Schema(description = "客户", requiredMode = Schema.RequiredMode.REQUIRED, example = "29746")
    @NotNull(message = "客户不能为空")
    private Long customerId;

    @Schema(description = "手机")
    private String mobile;

    @Schema(description = "品牌", example = "8302")
    private Long brandId;

    @Schema(description = "下单日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "下单日期不能为空")
    private LocalDate orderDate;

    @Schema(description = "物流", example = "27080")
    private Long logisticId;

    @Schema(description = "收货人")
    private String receiver;

    @Schema(description = "送货地址", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "送货地址不能为空")
    private String deliveryAddress;

    @Schema(description = "运费", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "运费不能为空")
    private BigDecimal freight;

    @Schema(description = "订单类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "成品帘、面料单")
    @NotEmpty(message = "订单类型不能为空")
    private String types;

    @Schema(description = "优惠金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "优惠金额不能为空")
    private BigDecimal discountAmount;

    @Schema(description = "总金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "总金额不能为空")
    private BigDecimal totalAmount;

    @Schema(description = "交付日期")
    private LocalDate deliveryDate;

    @Schema(description = "结算状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotEmpty(message = "结算状态不能为空")
    private String payStatus;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotEmpty(message = "状态不能为空")
    private String status;

    @Schema(description = "确认时间")
    private LocalDateTime confirmTime;

    @Schema(description = "是否加急", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否加急不能为空")
    private Boolean isExpedited;

    @Schema(description = "备注")
    private String note;

}