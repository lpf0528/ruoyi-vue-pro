package cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * 管理后台 - 成品订单-结构行详情 Response VO
 *
 * <p>在 {@link ZcSalesOrderStructureRespVO} 基础上扩展了关联表冗余名称字段，
 * 以及嵌套的用料明细列表，用于订单全量明细查询的嵌套返回结构。</p>
 */
@Schema(description = "管理后台 - 成品订单-结构行详情 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ZcSalesOrderStructureDetailRespVO extends ZcSalesOrderStructureRespVO {

    /** 结构名称，来自 zc_curtain_structure.name */
    @Schema(description = "结构名称")
    private String structureName;

    /** 安装工艺名称，来自 zc_curtain_install_process.name */
    @Schema(description = "安装工艺名称")
    private String installProcessName;

    /** 该结构行下的用料明细列表 */
    @Schema(description = "用料明细列表")
    private List<ZCSalesOrderMaterialDetailRespVO> elements;

}
