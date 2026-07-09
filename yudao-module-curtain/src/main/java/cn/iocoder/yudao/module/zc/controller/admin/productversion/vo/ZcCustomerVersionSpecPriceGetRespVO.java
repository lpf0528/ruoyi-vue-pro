package cn.iocoder.yudao.module.zc.controller.admin.productversion.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 客户版本销售授权价 - 单条查询响应 VO
 */
@Schema(description = "管理后台 - 客户版本规格授权价单条查询 Response VO")
@Data
public class ZcCustomerVersionSpecPriceGetRespVO {

    @Schema(description = "客户编号")
    private Long customerId;

    @Schema(description = "产品版本编号")
    private Long versionId;

    @Schema(description = "规格名称")
    private String spec;

    @Schema(description = "授权销售价格")
    private BigDecimal authorizedPrice;

}
