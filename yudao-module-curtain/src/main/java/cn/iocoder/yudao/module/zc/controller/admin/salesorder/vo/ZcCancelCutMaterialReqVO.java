package cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

/**
 * 成品订单撤销裁剪请求 VO
 */
@Schema(description = "管理后台 - 成品订单撤销裁剪请求 VO")
@Data
public class ZcCancelCutMaterialReqVO {

    /** 用料明细ID */
    @Schema(description = "用料明细ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "29")
    @NotNull(message = "用料明细ID不能为空")
    private Long materialId;

    /** 主操作人员 ID，关联 system_users.id */
    @Schema(description = "主操作人员ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "主操作人员不能为空")
    private Long masterId;

    /** 副操作人员 ID，可为空 */
    @Schema(description = "副操作人员ID", example = "2")
    private Long assistantId;

}
