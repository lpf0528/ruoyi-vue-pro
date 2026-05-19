package cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 管理后台 - 销售订单用料明细（嵌套创建）VO
 *
 * <p>用于订单整单创建接口中内嵌的用料明细，
 * orderId / orderStructureId 由 Service 层根据父级 ID 自动填充，无需前端传入。</p>
 */
@Schema(description = "管理后台 - 销售订单用料明细（嵌套创建）VO")
@Data
public class ZCSalesOrderMaterialCreateVO {

    /** 组件类型 */
    @Schema(description = "组件类型", example = "4206")
    private Long elementId;

    /** 货号（产品 ID） */
    @Schema(description = "货号", example = "24015")
    private Long productId;

    /** 批次 */
    @Schema(description = "批次", example = "25324")
    private Long batchId;

    /** 单价 */
    @Schema(description = "单价")
    private BigDecimal price;

    /** 用料数量 */
    @Schema(description = "用料")
    private BigDecimal quantity;

    /** 单位 */
    @Schema(description = "单位")
    private String unitValue;

    /** 折扣率 */
    @Schema(description = "折扣率")
    private BigDecimal discountRate;

    /** 小计金额 */
    @Schema(description = "小计")
    private BigDecimal amount;

    /** 备注 */
    @Schema(description = "备注")
    private String note;

}
