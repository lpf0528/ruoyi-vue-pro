package cn.iocoder.yudao.module.zc.controller.admin.productbatch.vo;

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

    @Schema(description = "批号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "批号不能为空")
    private String batchNo;

    @Schema(description = "入库日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "入库日期不能为空")
    private LocalDate inboundDate;

    @Schema(description = "产品", requiredMode = Schema.RequiredMode.REQUIRED, example = "7855")
    @NotNull(message = "产品不能为空")
    private Long productId;

    @Schema(description = "进货价", example = "6838")
    private BigDecimal inboundPrice;

    @Schema(description = "入库数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "入库数量不能为空")
    private BigDecimal inboundQuantity;

    @Schema(description = "剩余数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "剩余数量不能为空")
    private BigDecimal quantity;

    @Schema(description = "仓库", example = "5470")
    private Long warehouseId;

    @Schema(description = "供应商", example = "12241")
    private Long supplierId;

    @Schema(description = "备注")
    private String note;

}