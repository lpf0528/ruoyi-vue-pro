package cn.iocoder.yudao.module.zc.controller.admin.customerproductprice.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 客户产品销售授权价分页 Request VO")
@Data
public class ZcCustomerProductPricePageReqVO extends PageParam {

    @Schema(description = "客户", example = "8396")
    private Long customerId;

    @Schema(description = "产品", example = "9553")
    private Long productId;

}