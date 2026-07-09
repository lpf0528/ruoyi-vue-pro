package cn.iocoder.yudao.module.zc.controller.admin.customerbalancelog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 客户余额变动流水新增/修改 Request VO")
@Data
public class ZcCustomerBalanceLogSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "10914")
    private Long id;

    @Schema(description = "客户", requiredMode = Schema.RequiredMode.REQUIRED, example = "7166")
    @NotNull(message = "客户不能为空")
    private Long customerId;

    @Schema(description = "余额变动额", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "余额变动额不能为空")
    private BigDecimal changeAmount;

    @Schema(description = "变动前余额", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "变动前余额不能为空")
    private BigDecimal balanceBefore;

    @Schema(description = "变动后余额", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "变动后余额不能为空")
    private BigDecimal balanceAfter;

    @Schema(description = "业务类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotEmpty(message = "业务类型不能为空")
    private String bizType;

    @Schema(description = "关联单据类型", example = "1")
    private String refType;

    @Schema(description = "关联单据主键", example = "27872")
    private Long refId;

    @Schema(description = "关联单号快照")
    private String refNo;

    @Schema(description = "备注", example = "你猜")
    private String remark;

}