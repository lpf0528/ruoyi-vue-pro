package cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

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

}
