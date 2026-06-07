package cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 面料单产品行撤销裁剪请求 VO
 */
@Schema(description = "管理后台 - 面料单产品行撤销裁剪请求 VO")
@Data
public class ZcCancelCutProductReqVO {

    /** 产品行 ID（zc_sales_order_product.id） */
    @Schema(description = "产品行ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "29")
    @NotNull(message = "产品行ID不能为空")
    private Long id;

}
