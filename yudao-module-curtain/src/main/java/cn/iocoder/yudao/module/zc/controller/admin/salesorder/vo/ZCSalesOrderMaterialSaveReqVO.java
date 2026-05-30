package cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo;

import cn.iocoder.yudao.module.zc.framework.operatelog.core.ZcProductParseFunction;
import com.mzt.logapi.starter.annotation.DiffLogField;
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
    @DiffLogField(name = "销售单")
    @NotNull(message = "销售单不能为空")
    private Long orderId;

    @Schema(description = "结构行", requiredMode = Schema.RequiredMode.REQUIRED, example = "29364")
    @DiffLogField(name = "结构行")
    @NotNull(message = "结构行不能为空")
    private Long orderStructureId;

    @Schema(description = "组件类型", example = "4206")
    @DiffLogField(name = "组件类型")
    private Long elementId;

    @Schema(description = "货号", example = "24015")
    @DiffLogField(name = "货号", function = ZcProductParseFunction.NAME)
    private Long productId;

    @Schema(description = "批次", example = "25324")
    @DiffLogField(name = "批次")
    private Long batchId;

    @Schema(description = "单价", example = "7061")
    @DiffLogField(name = "单价")
    private BigDecimal price;

    @Schema(description = "用料")
    @DiffLogField(name = "用料")
    private BigDecimal quantity;

    @Schema(description = "单位")
    @DiffLogField(name = "单位")
    private String unitValue;

    @Schema(description = "折扣率")
    @DiffLogField(name = "折扣率")
    private BigDecimal discountRate;

    @Schema(description = "小计")
    @DiffLogField(name = "小计")
    private BigDecimal amount;

    @Schema(description = "备注")
    @DiffLogField(name = "备注")
    private String note;

}
