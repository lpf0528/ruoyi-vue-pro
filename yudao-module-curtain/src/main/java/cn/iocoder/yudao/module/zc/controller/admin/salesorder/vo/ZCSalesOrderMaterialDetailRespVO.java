package cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 管理后台 - 成品订单-用料明细详情 Response VO
 *
 * <p>在 {@link ZCSalesOrderMaterialRespVO} 基础上扩展了关联表的冗余名称字段，
 * 用于订单全量明细查询的嵌套返回结构。</p>
 */
@Schema(description = "管理后台 - 成品订单-用料明细详情 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ZCSalesOrderMaterialDetailRespVO extends ZCSalesOrderMaterialRespVO {

    /** 组件类型名称，来自 zc_curtain_structure_element.name */
    @Schema(description = "组件类型名称")
    private String elementName;

    /** 产品名称，来自 zc_product.name */
    @Schema(description = "产品名称")
    private String productName;

    /** 批次号，来自 zc_product_batch.batch_no */
    @Schema(description = "批次号")
    private String batchNo;

    /** 批次条码，来自 zc_product_batch.barcode */
    @Schema(description = "批次条码")
    private String barcode;

}
