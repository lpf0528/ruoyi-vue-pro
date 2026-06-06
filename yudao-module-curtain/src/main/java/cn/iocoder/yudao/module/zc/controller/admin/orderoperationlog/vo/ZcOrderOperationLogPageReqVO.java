package cn.iocoder.yudao.module.zc.controller.admin.orderoperationlog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 订单操作记录分页 Request VO")
@Data
public class ZcOrderOperationLogPageReqVO extends PageParam {

    @Schema(description = "销售订单 ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "订单 ID 不能为空")
    private Long orderId;

    @Schema(description = "操作对象类型：ORDER / CURTAIN / MATERIAL", example = "CURTAIN")
    private String targetType;

    @Schema(description = "操作对象 ID（窗帘行 ID 或用料明细 ID）", example = "101")
    private Long targetId;

    @Schema(description = "操作类型：CONFIRM / CANCEL_CONFIRM / PACK / CANCEL_PACK / SHIP / CANCEL_SHIP / CUT / CANCEL_CUT", example = "PACK")
    private String operateType;

    @Schema(description = "是否已撤销", example = "false")
    private Boolean revoked;

}
