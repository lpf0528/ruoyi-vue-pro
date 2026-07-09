package cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    /** 序号，同一订单内产品行的显示顺序，从 1 开始 */
    @Schema(description = "序号，同一订单内产品行的显示顺序，从 1 开始")
    private Integer index;

    /** 产品行状态，参见 zc_order_status 字典 */
    @Schema(description = "产品行状态，参见 zc_order_status 字典")
    private String status;

    /** 裁剪数量；裁剪后记录实际出库数量，撤销裁剪后为 null */
    @Schema(description = "裁剪数量")
    private java.math.BigDecimal cutQuantity;

    /** 发货时间；发货后记录，撤销发货后为 null */
    @Schema(description = "发货时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime shipTime;

}
