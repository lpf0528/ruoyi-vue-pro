package cn.iocoder.yudao.module.zc.controller.admin.processnode.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 管理后台 - 撤销工序记录 Request VO
 */
@Schema(description = "管理后台 - 撤销工序记录 Request VO")
@Data
public class ZcOrderProcessRecordRevokeReqVO {

    @Schema(description = "工序记录 ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "工序记录 ID 不能为空")
    private Long id;

    @Schema(description = "撤销原因备注")
    private String note;

}
