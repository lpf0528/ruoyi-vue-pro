package cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import cn.idev.excel.annotation.*;

@Schema(description = "管理后台 - 成品订单-用料明细 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ZCSalesOrderMaterialRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "8732")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "销售单", requiredMode = Schema.RequiredMode.REQUIRED, example = "19882")
    @ExcelProperty("销售单")
    private Long orderId;

    @Schema(description = "结构行", requiredMode = Schema.RequiredMode.REQUIRED, example = "29364")
    @ExcelProperty("结构行")
    private Long orderStructureId;

    @Schema(description = "组件类型", example = "4206")
    @ExcelProperty("组件类型")
    private Long elementId;

    @Schema(description = "货号", requiredMode = Schema.RequiredMode.REQUIRED, example = "24015")
    @ExcelProperty("货号")
    private Long productId;

    @Schema(description = "批次", example = "25324")
    @ExcelProperty("批次")
    private Long batchId;

    @Schema(description = "单价", example = "7061")
    @ExcelProperty("单价")
    private BigDecimal price;

    @Schema(description = "用料")
    @ExcelProperty("用料")
    private BigDecimal quantity;

    @Schema(description = "单位")
    @ExcelProperty("单位")
    private String unitValue;

    @Schema(description = "折扣率")
    @ExcelProperty("折扣率")
    private BigDecimal discountRate;

    @Schema(description = "小计")
    @ExcelProperty("小计")
    private BigDecimal amount;

    @Schema(description = "备注")
    @ExcelProperty("备注")
    private String note;

    @Schema(description = "配料状态：NOT_PEILIAO=未配料，HAVE_PEILIAO=已配料")
    @ExcelProperty("配料状态")
    private String status;

    @Schema(description = "裁剪数量")
    @ExcelProperty("裁剪数量")
    private BigDecimal cutQuantity;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}