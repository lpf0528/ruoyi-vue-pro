package cn.iocoder.yudao.module.zc.controller.admin.productbatch.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 产品批次新增/修改 Request VO")
@Data
public class ZcProductBatchSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "31107")
    private Long id;

    @Schema(description = "入库日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "入库日期不能为空")
    private LocalDate inboundDate;

    @Schema(description = "产品", requiredMode = Schema.RequiredMode.REQUIRED, example = "5889")
    @NotNull(message = "产品不能为空")
    private Long productId;

    @Schema(description = "入库数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "入库数量不能为空")
    private BigDecimal inboundQuantity;

    @Schema(description = "剩余数量")
    private BigDecimal quantity;

    @Schema(description = "仓库", example = "17396")
    private Long warehouseId;

    @Schema(description = "供应商", example = "28315")
    private Long supplierId;

    @Schema(description = "备注")
    private String note;

}