package cn.iocoder.yudao.module.zc.controller.admin.processnode.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 管理后台 - 标记工序完成 Request VO
 */
@Schema(description = "管理后台 - 标记工序完成 Request VO")
@Data
public class ZcOrderProcessRecordCompleteReqVO {

    @Schema(description = "工序记录 ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "工序记录 ID 不能为空")
    private Long id;

    @Schema(description = "完成备注")
    private String note;

}
