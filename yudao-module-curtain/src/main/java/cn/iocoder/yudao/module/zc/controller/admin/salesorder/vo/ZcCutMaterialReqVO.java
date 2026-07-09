package cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 成品订单裁剪请求 VO
 *
 * <p>用于成品订单用料明细裁剪操作：绑定批次、记录裁剪数量、扣减批次库存、更新配料状态。</p>
 */
@Schema(description = "管理后台 - 成品订单裁剪请求 VO")
@Data
public class ZcCutMaterialReqVO {

    /** 用料明细ID（zc_sales_order_material.id） */
    @Schema(description = "用料明细ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "29")
    @NotNull(message = "用料明细ID不能为空")
    private Long id;

    /** 批次ID（zc_product_batch.id） */
    @Schema(description = "批次ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "17")
    @NotNull(message = "批次ID不能为空")
    private Long batchId;

    /** 裁剪数量，必须大于0 */
    @Schema(description = "裁剪数量", requiredMode = Schema.RequiredMode.REQUIRED, example = "12")
    @NotNull(message = "裁剪数量不能为空")
    @DecimalMin(value = "0", inclusive = false, message = "裁剪数量必须大于0")
    private BigDecimal cutQuantity;

    /** 主操作人员 ID，关联 system_users.id */
    @Schema(description = "主操作人员ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "主操作人员不能为空")
    private Long masterId;

    /** 副操作人员 ID，可为空 */
    @Schema(description = "副操作人员ID", example = "2")
    private Long assistantId;

}
