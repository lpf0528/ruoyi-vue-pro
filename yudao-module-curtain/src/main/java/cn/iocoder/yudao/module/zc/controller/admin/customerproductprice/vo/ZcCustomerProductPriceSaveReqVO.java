package cn.iocoder.yudao.module.zc.controller.admin.customerproductprice.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 客户产品销售授权价新增/修改 Request VO")
@Data
public class ZcCustomerProductPriceSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "30077")
    private Long id;

    @Schema(description = "客户", requiredMode = Schema.RequiredMode.REQUIRED, example = "8396")
    @NotNull(message = "客户不能为空")
    private Long customerId;

    @Schema(description = "产品", requiredMode = Schema.RequiredMode.REQUIRED, example = "9553")
    @NotNull(message = "产品不能为空")
    private Long productId;

    @Schema(description = "授权价格", requiredMode = Schema.RequiredMode.REQUIRED, example = "27736")
    @NotNull(message = "授权价格不能为空")
    private BigDecimal authorizedPrice;

}