package cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 面料单产品行裁剪请求 VO
 *
 * <p>用于面料单产品行裁剪操作：记录裁剪数量、扣减批次库存、更新配料状态。
 * 与成品订单裁剪不同，批次 ID 已在下单时绑定，此处无需重复传入。</p>
 */
@Schema(description = "管理后台 - 面料单产品行裁剪请求 VO")
@Data
public class ZcCutProductReqVO {

    /** 产品行 ID（zc_sales_order_product.id） */
    @Schema(description = "产品行ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "29")
    @NotNull(message = "产品行ID不能为空")
    private Long id;

    /** 裁剪数量，必须大于 0 */
    @Schema(description = "裁剪数量", requiredMode = Schema.RequiredMode.REQUIRED, example = "12.5")
    @NotNull(message = "裁剪数量不能为空")
    @DecimalMin(value = "0", inclusive = false, message = "裁剪数量必须大于0")
    private BigDecimal cutQuantity;

}
