package cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 管理后台 - 产品类销售订单-产品行 Response VO
 *
 * <p>对应 zc_sales_order_product 表的一行，冗余了产品名称、批次号，
 * 供前端直接展示无需二次请求。</p>
 *
 * @author 01Coder
 */
@Schema(description = "管理后台 - 产品类销售订单-产品行 Response VO")
@Data
public class ZcSalesOrderProductLineRespVO {

    /** 产品行 ID */
    @Schema(description = "产品行 ID", example = "1")
    private Long id;

    /** 产品 ID */
    @Schema(description = "产品 ID", example = "100")
    private Long productId;

    /** 产品名称（冗余，避免前端二次请求） */
    @Schema(description = "产品名称")
    private String productName;

    /** 批次 ID */
    @Schema(description = "批次 ID", example = "200")
    private Long batchId;

    /** 批次号（冗余，避免前端二次请求） */
    @Schema(description = "批次号")
    private String batchNo;

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
