package cn.iocoder.yudao.module.zc.controller.admin.processnode.vo;

import cn.iocoder.yudao.module.zc.framework.operatelog.core.ZcProcessNodeParseFunction;
import com.mzt.logapi.starter.annotation.DiffLogField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 管理后台 - 新增工序记录 Request VO
 */
@Schema(description = "管理后台 - 新增工序记录 Request VO")
@Data
public class ZcOrderProcessRecordSaveReqVO {

    @Schema(description = "销售订单 ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @DiffLogField(name = "销售订单")
    @NotNull(message = "销售订单 ID 不能为空")
    private Long orderId;

    @Schema(description = "工序节点 ID（必须是当前员工已绑定的节点）", requiredMode = Schema.RequiredMode.REQUIRED, example = "3")
    @DiffLogField(name = "工序节点", function = ZcProcessNodeParseFunction.NAME)
    @NotNull(message = "工序节点不能为空")
    private Long nodeId;

    @Schema(description = "备注（如发现异常情况的说明）")
    @DiffLogField(name = "备注")
    private String note;

    @Schema(description = "现场照片 URL 列表")
    private List<String> imageUrls;

}
