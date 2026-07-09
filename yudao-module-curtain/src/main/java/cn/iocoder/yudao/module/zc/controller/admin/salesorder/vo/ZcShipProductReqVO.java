package cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 面料单产品行发货请求 VO
 *
 * <p>将指定产品行标记为已发货，同步联动更新订单主表状态（BUFEN_FAHUO 或 FAHUO）。</p>
 */
@Schema(description = "管理后台 - 面料单产品行发货请求 VO")
@Data
public class ZcShipProductReqVO {

    /** 产品行 ID（zc_sales_order_product.id） */
    @Schema(description = "产品行ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "29")
    @NotNull(message = "产品行ID不能为空")
    private Long id;

}
