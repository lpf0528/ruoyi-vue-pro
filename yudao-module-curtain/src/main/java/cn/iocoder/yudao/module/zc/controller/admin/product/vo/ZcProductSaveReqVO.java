package cn.iocoder.yudao.module.zc.controller.admin.product.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 货号档案新增/修改 Request VO")
@Data
public class ZcProductSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "21159")
    private Long id;

    @Schema(description = "产品名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @NotEmpty(message = "产品名称不能为空")
    private String name;

    @Schema(description = "版本", example = "17507")
    private Long versionId;

    @Schema(description = "进货价", example = "14151")
    private BigDecimal inboundPrice;

    @Schema(description = "A 类销售价", example = "12540")
    private BigDecimal aPrice;

    @Schema(description = "供应商", example = "25473")
    private Long supplierId;

    @Schema(description = "采购类型", example = "0 整采 1 零采")
    private Integer purchaseType;

    @Schema(description = "备注")
    private String note;

}