package cn.iocoder.yudao.module.zc.controller.admin.customerproductprice.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import cn.idev.excel.annotation.*;

@Schema(description = "管理后台 - 客户产品销售授权价 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ZcCustomerProductPriceRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "30077")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "客户", requiredMode = Schema.RequiredMode.REQUIRED, example = "8396")
    @ExcelProperty("客户")
    private Long customerId;

    @Schema(description = "产品", requiredMode = Schema.RequiredMode.REQUIRED, example = "9553")
    @ExcelProperty("产品")
    private Long productId;

    @Schema(description = "授权价格", requiredMode = Schema.RequiredMode.REQUIRED, example = "27736")
    @ExcelProperty("授权价格")
    private BigDecimal authorizedPrice;

    @Schema(description = "创建者")
    @ExcelProperty("创建者")
    private String creator;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}