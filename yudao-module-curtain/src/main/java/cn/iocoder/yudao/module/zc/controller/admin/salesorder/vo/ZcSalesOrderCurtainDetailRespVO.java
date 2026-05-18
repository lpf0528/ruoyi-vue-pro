package cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * 管理后台 - 成品订单-窗帘行详情 Response VO
 *
 * <p>在 {@link ZcSalesOrderCurtainRespVO} 基础上扩展了关联表冗余名称字段，
 * 以及嵌套的结构行列表，用于订单全量明细查询的嵌套返回结构。</p>
 */
@Schema(description = "管理后台 - 成品订单-窗帘行详情 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ZcSalesOrderCurtainDetailRespVO extends ZcSalesOrderCurtainRespVO {

    /** 窗帘款式名称，来自 zc_curtain.name */
    @Schema(description = "窗帘款式名称")
    private String curtainName;

    /** 该窗帘行下的结构行列表 */
    @Schema(description = "结构行列表")
    private List<ZcSalesOrderStructureDetailRespVO> structures;

}
