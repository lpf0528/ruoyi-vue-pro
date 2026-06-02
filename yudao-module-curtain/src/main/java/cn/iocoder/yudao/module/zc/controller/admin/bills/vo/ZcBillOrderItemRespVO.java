package cn.iocoder.yudao.module.zc.controller.admin.bills.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 管理后台 - 收款单订单分摊明细 Response VO
 *
 * <p>返回 zc_bill_order_items 的基础字段，并 JOIN zc_sales_order 带出订单号。</p>
 */
@Schema(description = "管理后台 - 收款单订单分摊明细 Response VO")
@Data
public class ZcBillOrderItemRespVO {

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "关联收款单 ID")
    private Long billId;

    @Schema(description = "关联销售订单 ID")
    private Long orderId;

    @Schema(description = "订单号", example = "ZC120260519000001")
    private String orderNo;

    @Schema(description = "本次分摊金额")
    private BigDecimal allocatedAmount;

    @Schema(description = "备注")
    private String note;

}
