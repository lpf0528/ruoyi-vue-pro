package cn.iocoder.yudao.module.zc.controller.admin.processnode.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 管理后台 - 订单工序记录 Response VO
 */
@Schema(description = "管理后台 - 订单工序记录 Response VO")
@Data
public class ZcOrderProcessRecordRespVO {

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "销售订单 ID")
    private Long orderId;

    @Schema(description = "窗帘行 ID")
    private Long curtainId;

    @Schema(description = "结构行 ID")
    private Long structureId;

    @Schema(description = "用料明细 ID")
    private Long materialId;

    @Schema(description = "工序节点 ID")
    private Long nodeId;

    @Schema(description = "工序名称（快照）")
    private String nodeName;

    @Schema(description = "状态：1=完成，2=撤销")
    private Integer status;

    @Schema(description = "主操作人员 ID")
    private Long masterId;

    @Schema(description = "主操作人员名称")
    private String masterName;

    @Schema(description = "副操作人员 ID")
    private Long assistantId;

    @Schema(description = "副操作人员名称")
    private String assistantName;

    @Schema(description = "备注")
    private String note;

    @Schema(description = "现场照片 URL 列表")
    private List<String> imageUrls;

    @Schema(description = "记录创建时间")
    private LocalDateTime createTime;

    @Schema(description = "最后更新时间（撤销时更新）")
    private LocalDateTime updateTime;

}
