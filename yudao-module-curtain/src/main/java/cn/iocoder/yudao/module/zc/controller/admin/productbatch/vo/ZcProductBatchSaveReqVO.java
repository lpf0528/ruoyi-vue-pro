package cn.iocoder.yudao.module.zc.controller.admin.productbatch.vo;

import cn.iocoder.yudao.module.zc.framework.operatelog.core.ZcProductParseFunction;
import cn.iocoder.yudao.module.zc.framework.operatelog.core.ZcSupplierParseFunction;
import cn.iocoder.yudao.module.zc.framework.operatelog.core.ZcWarehouseParseFunction;
import com.mzt.logapi.starter.annotation.DiffLogField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 产品批次新增/修改 Request VO")
@Data
public class ZcProductBatchSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "209")
    private Long id;

    @Schema(description = "入库日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @DiffLogField(name = "入库日期")
    @NotNull(message = "入库日期不能为空")
    private LocalDate inboundDate;

    @Schema(description = "产品", requiredMode = Schema.RequiredMode.REQUIRED, example = "7855")
    @DiffLogField(name = "产品", function = ZcProductParseFunction.NAME)
    @NotNull(message = "产品不能为空")
    private Long productId;

    @Schema(description = "进货价", example = "6838")
    @DiffLogField(name = "进货价")
    private BigDecimal inboundPrice;

    @Schema(description = "入库数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @DiffLogField(name = "入库数量")
    @NotNull(message = "入库数量不能为空")
    private BigDecimal inboundQuantity;

    @Schema(description = "剩余数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @DiffLogField(name = "剩余数量")
    @NotNull(message = "剩余数量不能为空")
    private BigDecimal quantity;

    @Schema(description = "仓库", example = "5470")
    @DiffLogField(name = "仓库", function = ZcWarehouseParseFunction.NAME)
    private Long warehouseId;

    @Schema(description = "供应商", example = "12241")
    @DiffLogField(name = "供应商", function = ZcSupplierParseFunction.NAME)
    private Long supplierId;

    @Schema(description = "备注")
    @DiffLogField(name = "备注")
    private String note;

}
