package cn.iocoder.yudao.module.zc.controller.admin.processnode.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 管理后台 - 当日员工节点用料统计 Response VO
 *
 * @author 01Coder
 */
@Schema(description = "管理后台 - 当日员工节点用料统计 Response VO")
@Data
public class ZcOrderProcessRecordTodayUserMaterialRespVO {

    @Schema(description = "主操作人员 ID")
    private Long masterId;

    @Schema(description = "主操作人员姓名")
    private String masterName;

    @Schema(description = "节点用料统计列表")
    private List<NodeStat> nodeStats;

    @Schema(description = "管理后台 - 节点用料统计")
    @Data
    public static class NodeStat {

        @Schema(description = "工序节点 ID")
        private Long nodeId;

        @Schema(description = "工序节点名称")
        private String nodeName;

        @Schema(description = "组件用料列表")
        private List<MaterialStat> materials;
    }

    @Schema(description = "管理后台 - 组件用料统计")
    @Data
    public static class MaterialStat {

        @Schema(description = "用料组件 ID")
        private Long elementId;

        @Schema(description = "用料组件名称")
        private String elementName;

        @Schema(description = "工序次数")
        private Long processCount;

        @Schema(description = "工序用料合计")
        private BigDecimal totalQuantity;
    }

}
