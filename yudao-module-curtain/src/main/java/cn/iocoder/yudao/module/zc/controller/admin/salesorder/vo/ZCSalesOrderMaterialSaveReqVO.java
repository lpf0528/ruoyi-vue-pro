package cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 成品订单-用料明细新增/修改 Request VO")
@Data
public class ZCSalesOrderMaterialSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "8732")
    private Long id;

    @Schema(description = "销售单", requiredMode = Schema.RequiredMode.REQUIRED, example = "19882")
    @NotNull(message = "销售单不能为空")
    private Long orderId;

    @Schema(description = "结构行", requiredMode = Schema.RequiredMode.REQUIRED, example = "29364")
    @NotNull(message = "结构行不能为空")
    private Long orderStructureId;

    @Schema(description = "组件类型", example = "4206")
    private Long elementId;

    @Schema(description = "货号", requiredMode = Schema.RequiredMode.REQUIRED, example = "24015")
    @NotNull(message = "货号不能为空")
    private Long productId;

    @Schema(description = "批次", example = "25324")
    private Long batchId;

    @Schema(description = "单价", example = "7061")
    private BigDecimal price;

    @Schema(description = "用料")
    private BigDecimal quantity;

    @Schema(description = "单位")
    private String unitValue;

    @Schema(description = "折扣率")
    private BigDecimal discountRate;

    @Schema(description = "小计")
    private BigDecimal amount;

    @Schema(description = "备注")
    private String note;

}