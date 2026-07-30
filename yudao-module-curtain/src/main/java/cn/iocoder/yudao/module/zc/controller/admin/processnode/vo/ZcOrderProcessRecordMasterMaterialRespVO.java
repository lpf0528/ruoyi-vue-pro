package cn.iocoder.yudao.module.zc.controller.admin.processnode.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 管理后台 - 操作员节点用料统计 Response VO
 *
 * @author 01Coder
 */
@Schema(description = "管理后台 - 操作员节点用料统计 Response VO")
@Data
public class ZcOrderProcessRecordMasterMaterialRespVO {

    @Schema(description = "工序次数")
    private Long processCount;

    @Schema(description = "工序用料合计")
    private BigDecimal totalQuantity;

}
