package cn.iocoder.yudao.module.zc.controller.admin.bills.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 收支账单新增/修改 Request VO")
@Data
public class ZcBillsSaveReqVO {

    @Schema(description = "主键（更新时传入）", example = "5800")
    private Long id;

    @Schema(description = "收款日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "收款日期不能为空")
    private LocalDate billDate;

    @Schema(description = "客户 ID", example = "1212")
    private Long customerId;

    @Schema(description = "优惠金额")
    private BigDecimal discountAmount;

    @Schema(description = "实收金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "实收金额不能为空")
    private BigDecimal actualAmount;

    @Schema(description = "收支方式 ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "23")
    @NotNull(message = "收支方式不能为空")
    private Long billMethodId;

    @Schema(description = "备注")
    private String note;

    @Schema(description = "账单附件 URL 列表")
    private List<String> attachments;

    @Schema(description = "收款订单分摊明细", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "订单分摊明细不能为空")
    @Valid
    private List<ZcBillOrderItemReqVO> orderItems;

}