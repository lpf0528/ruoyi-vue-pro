package cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 成品订单-窗帘行操作请求 VO
 *
 * <p>用于打包、发货、取消打包、取消发货等窗帘行状态变更操作，
 * 需指定操作的窗帘行 ID 以及主/副操作人员。</p>
 */
@Schema(description = "管理后台 - 成品订单-窗帘行操作请求 VO")
@Data
public class ZcSalesOrderCurtainOperateReqVO {

    /** 窗帘行 ID（zc_sales_order_curtain.id） */
    @Schema(description = "窗帘行 ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "窗帘行ID不能为空")
    private Long id;

    /** 主操作人员 ID（zc_workshop_user.id） */
    @Schema(description = "主操作人员 ID（zc_workshop_user.id）", requiredMode = Schema.RequiredMode.REQUIRED, example = "5")
    @NotNull(message = "主操作人员不能为空")
    private Long masterId;

    /** 副操作人员 ID（zc_workshop_user.id，可为空） */
    @Schema(description = "副操作人员 ID（zc_workshop_user.id，可为空）", example = "6")
    private Long assistantId;

}
