package cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo;

import lombok.*;

import java.time.LocalDate;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 销售订单分页 Request VO")
@Data
public class ZcSalesOrderPageReqVO extends PageParam {

    @Schema(description = "订单号")
    private String orderNo;

    @Schema(description = "客户", example = "29746")
    private Long customerId;

    @Schema(description = "品牌", example = "8302")
    private Long brandId;

    @Schema(description = "下单日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDate[] orderDate;

    @Schema(description = "物流", example = "27080")
    private Long logisticId;

    @Schema(description = "订单类型", example = "成品帘、面料单")
    private String types;

    @Schema(description = "交付日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDate[] deliveryDate;

    @Schema(description = "结算状态（多选），可选值：paid / unpaid / partialpaid", example = "[\"paid\",\"unpaid\"]")
    private List<String> payStatus;

    @Schema(description = "订单状态（多选），可选值参见 ZcSalesOrderStatusEnum，如 [\"confirmed\",\"fahuo\"]", example = "[\"confirmed\"]")
    private List<String> status;

    @Schema(description = "是否已确认：true=已确认（confirm_time 不为空），false=未确认（confirm_time 为空）")
    private Boolean isConfirm;

    @Schema(description = "是否加急")
    private Boolean isExpedited;

    @Schema(description = "是否包含未确认订单（status = unconfirmed），为 false 时过滤掉未确认订单，默认 null/true 全部包含")
    private Boolean includeUnconfirmed;

    @Schema(description = "是否排除已完成订单（status = complete），为 true 时过滤掉已完成订单")
    private Boolean excludeComplete;

}