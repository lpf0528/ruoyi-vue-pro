package cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * 管理后台 - 销售订单完整详情 Response VO
 *
 * <p>继承 {@link ZcSalesOrderRespVO} 的所有订单主表字段，
 * 在此基础上追加 curtains 节点，包含三层嵌套的窗帘行→结构行→用料明细。</p>
 */
@Schema(description = "管理后台 - 销售订单完整详情 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ZcSalesOrderDetailRespVO extends ZcSalesOrderRespVO {

    /** 窗帘行列表（每行含结构行，每个结构行含用料明细） */
    @Schema(description = "窗帘行列表（三层嵌套：窗帘行→结构行→用料明细）")
    private List<ZcSalesOrderCurtainDetailRespVO> curtains;

}
