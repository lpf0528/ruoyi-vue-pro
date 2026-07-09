package cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 管理后台 - 销售订单按客户统计 Response VO
 */
@Schema(description = "管理后台 - 销售订单按客户统计 Response VO")
@Data
public class ZcSalesOrderCustomerStatisticsRespVO {

    @Schema(description = "客户编号", example = "29746")
    private Long customerId;

    @Schema(description = "客户名称", example = "张三窗帘店")
    private String customerName;

    @Schema(description = "订单数", example = "12")
    private Long orderCount;

    @Schema(description = "订单金额合计（amount 字段求和）", example = "15800.00")
    private BigDecimal totalAmount;

    @Schema(description = "已收金额合计（amount_received 字段求和）", example = "12000.00")
    private BigDecimal totalAmountReceived;

    @Schema(description = "未收金额合计（各订单 amount - amount_received 求和）", example = "3800.00")
    private BigDecimal unpaidAmount;

}
