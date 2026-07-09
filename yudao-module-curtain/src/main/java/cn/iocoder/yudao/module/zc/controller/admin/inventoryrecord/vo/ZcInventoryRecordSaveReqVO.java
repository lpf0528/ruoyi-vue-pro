package cn.iocoder.yudao.module.zc.controller.admin.inventoryrecord.vo;

import cn.iocoder.yudao.module.zc.framework.operatelog.core.ZcProductParseFunction;
import com.mzt.logapi.starter.annotation.DiffLogField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 盘点记录新增 Request VO")
@Data
public class ZcInventoryRecordSaveReqVO {

    @Schema(description = "产品", requiredMode = Schema.RequiredMode.REQUIRED, example = "9127")
    @DiffLogField(name = "产品", function = ZcProductParseFunction.NAME)
    @NotNull(message = "产品不能为空")
    private Long productId;

    @Schema(description = "批次", requiredMode = Schema.RequiredMode.REQUIRED, example = "8051")
    @DiffLogField(name = "批次")
    @NotNull(message = "批次不能为空")
    private Long batchId;

    @Schema(description = "盘点前数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @DiffLogField(name = "盘点前数量")
    @NotNull(message = "盘点前数量不能为空")
    private BigDecimal oldQuantity;

    @Schema(description = "盘点后数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @DiffLogField(name = "盘点后数量")
    @NotNull(message = "盘点后数量不能为空")
    private BigDecimal newQuantity;

    @Schema(description = "备注")
    @DiffLogField(name = "备注")
    private String note;

    @Schema(description = "规格")
    @DiffLogField(name = "规格")
    private String spec;

}
