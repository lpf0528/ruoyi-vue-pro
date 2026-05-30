package cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo;

import cn.iocoder.yudao.module.zc.framework.operatelog.core.ZcBrandParseFunction;
import cn.iocoder.yudao.module.zc.framework.operatelog.core.ZcCustomerParseFunction;
import cn.iocoder.yudao.module.zc.framework.operatelog.core.ZcLogisticsParseFunction;
import com.mzt.logapi.starter.annotation.DiffLogField;
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
    @DiffLogField(name = "订单号")
    @NotEmpty(message = "订单号不能为空")
    private String orderNo;

    @Schema(description = "客户", requiredMode = Schema.RequiredMode.REQUIRED, example = "29746")
    @DiffLogField(name = "客户", function = ZcCustomerParseFunction.NAME)
    @NotNull(message = "客户不能为空")
    private Long customerId;

    @Schema(description = "手机")
    @DiffLogField(name = "手机")
    private String mobile;

    @Schema(description = "品牌", example = "8302")
    @DiffLogField(name = "品牌", function = ZcBrandParseFunction.NAME)
    private Long brandId;

    @Schema(description = "下单日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @DiffLogField(name = "下单日期")
    @NotNull(message = "下单日期不能为空")
    private LocalDate orderDate;

    @Schema(description = "物流", example = "27080")
    @DiffLogField(name = "物流", function = ZcLogisticsParseFunction.NAME)
    private Long logisticId;

    @Schema(description = "收货人")
    @DiffLogField(name = "收货人")
    private String receiver;

    @Schema(description = "送货地址", requiredMode = Schema.RequiredMode.REQUIRED)
    @DiffLogField(name = "送货地址")
    @NotEmpty(message = "送货地址不能为空")
    private String deliveryAddress;

    @Schema(description = "运费", requiredMode = Schema.RequiredMode.REQUIRED)
    @DiffLogField(name = "运费")
    @NotNull(message = "运费不能为空")
    private BigDecimal freight;

    @Schema(description = "订单类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "成品帘、面料单")
    @DiffLogField(name = "订单类型")
    @NotEmpty(message = "订单类型不能为空")
    private String types;

    @Schema(description = "优惠金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @DiffLogField(name = "优惠金额")
    @NotNull(message = "优惠金额不能为空")
    private BigDecimal discountAmount;

    @Schema(description = "总金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @DiffLogField(name = "总金额")
    @NotNull(message = "总金额不能为空")
    private BigDecimal totalAmount;

    @Schema(description = "交付日期")
    @DiffLogField(name = "交付日期")
    private LocalDate deliveryDate;

    @Schema(description = "结算状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @DiffLogField(name = "结算状态")
    @NotEmpty(message = "结算状态不能为空")
    private String payStatus;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @DiffLogField(name = "状态")
    @NotEmpty(message = "状态不能为空")
    private String status;

    @Schema(description = "确认时间")
    @DiffLogField(name = "确认时间")
    private LocalDateTime confirmTime;

    @Schema(description = "是否加急", requiredMode = Schema.RequiredMode.REQUIRED)
    @DiffLogField(name = "是否加急")
    @NotNull(message = "是否加急不能为空")
    private Boolean isExpedited;

    @Schema(description = "备注")
    @DiffLogField(name = "备注")
    private String note;

}
