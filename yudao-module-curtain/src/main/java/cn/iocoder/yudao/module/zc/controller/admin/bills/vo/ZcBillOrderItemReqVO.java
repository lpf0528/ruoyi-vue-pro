package cn.iocoder.yudao.module.zc.controller.admin.bills.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 收款单 - 订单分摊明细 Request VO
 */
@Schema(description = "收款单 - 订单分摊明细")
@Data
public class ZcBillOrderItemReqVO {

    /** 关联订单 ID */
    @Schema(description = "订单 ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "订单 ID 不能为空")
    private Long orderId;

    /** 本次分摊金额 */
    @Schema(description = "本次分摊金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "分摊金额不能为空")
    private BigDecimal allocatedAmount;

}
