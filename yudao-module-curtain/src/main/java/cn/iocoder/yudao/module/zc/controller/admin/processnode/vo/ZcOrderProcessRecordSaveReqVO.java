package cn.iocoder.yudao.module.zc.controller.admin.processnode.vo;

import cn.iocoder.yudao.module.zc.framework.operatelog.core.ZcProcessNodeParseFunction;
import com.mzt.logapi.starter.annotation.DiffLogField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * 管理后台 - 新增工序记录 Request VO
 *
 * <p>定位 ID 按最细粒度归一化后落库：</p>
 * <ul>
 *   <li>用料级（materialId 有值）：自动补齐 curtainId、structureId</li>
 *   <li>结构级（structureId 有值）：自动补齐 curtainId，materialId 置空</li>
 *   <li>窗帘级（仅 curtainId）：structureId、materialId 置空</li>
 * </ul>
 * 记录一经创建即表示该工序已完成（status=1）。
 */
@Schema(description = "管理后台 - 新增工序记录 Request VO")
@Data
public class ZcOrderProcessRecordSaveReqVO {

    @Schema(description = "销售订单 ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @DiffLogField(name = "销售订单")
    @NotNull(message = "销售订单 ID 不能为空")
    private Long orderId;

    @Schema(description = "窗帘行 ID（可为空）", example = "10")
    private Long curtainId;

    @Schema(description = "结构行 ID（可为空）", example = "20")
    private Long structureId;

    @Schema(description = "用料明细 ID（可为空）", example = "30")
    private Long materialId;

    @Schema(description = "工序节点 ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "3")
    @DiffLogField(name = "工序节点", function = ZcProcessNodeParseFunction.NAME)
    @NotNull(message = "工序节点不能为空")
    private Long nodeId;

    @Schema(description = "主操作人员 ID（zc_workshop_user.id）", requiredMode = Schema.RequiredMode.REQUIRED, example = "5")
    @NotNull(message = "主操作人员不能为空")
    private Long masterId;

    @Schema(description = "副操作人员 ID（zc_workshop_user.id，可为空）", example = "6")
    private Long assistantId;

    @Schema(description = "备注（如发现异常情况的说明）")
    @DiffLogField(name = "备注")
    private String note;

    @Schema(description = "现场照片 URL 列表")
    private List<String> imageUrls;

}
