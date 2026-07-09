package cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 产品类销售订单 - 单条产品行请求 VO
 *
 * <p>对应 zc_sales_order_product 表的一行记录，
 * 由 {@link ZcSalesOrderProductCreateReqVO#getBatchs()} 携带。</p>
 *
 * @author 01Coder
 */
@Schema(description = "管理后台 - 产品类销售订单-产品行 VO")
@Data
public class ZcSalesOrderProductBatchCreateVO {

    /** 产品 ID，必填 */
    @Schema(description = "产品 ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "产品不能为空")
    private Long productId;

    /** 批次 ID */
    @Schema(description = "批次 ID")
    private Long batchId;

    /** 数量 */
    @Schema(description = "数量")
    private BigDecimal quantity;

    /** 单价 */
    @Schema(description = "单价")
    private BigDecimal price;

    /** 行小计金额 */
    @Schema(description = "行小计金额")
    private BigDecimal amount;

    /** 备注 */
    @Schema(description = "备注")
    private String note;

}
