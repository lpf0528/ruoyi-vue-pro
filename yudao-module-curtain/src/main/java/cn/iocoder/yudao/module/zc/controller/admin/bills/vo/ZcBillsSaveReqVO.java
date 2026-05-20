package cn.iocoder.yudao.module.zc.controller.admin.bills.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 收支账单新增/修改 Request VO")
@Data
public class ZcBillsSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "5800")
    private Long id;

    @Schema(description = "单号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "单号不能为空")
    private String billNo;

    @Schema(description = "付款时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "付款时间不能为空")
    private LocalDate billDate;

    @Schema(description = "财务人员", requiredMode = Schema.RequiredMode.REQUIRED, example = "25823")
    @NotNull(message = "财务人员不能为空")
    private Long billUserId;

    @Schema(description = "客户", requiredMode = Schema.RequiredMode.REQUIRED, example = "11545")
    @NotNull(message = "客户不能为空")
    private Long customerId;

    @Schema(description = "优惠金额")
    private BigDecimal discountAmount;

    @Schema(description = "实收金额 ", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "实收金额 不能为空")
    private BigDecimal actualAmount;

    @Schema(description = "收支方式", requiredMode = Schema.RequiredMode.REQUIRED, example = "19006")
    @NotNull(message = "收支方式不能为空")
    private Long billMethodId;

    @Schema(description = "备注")
    private String note;

}