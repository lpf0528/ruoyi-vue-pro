package cn.iocoder.yudao.module.zc.controller.admin.productversion.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 客户版本销售授权价 - 响应 VO（含版本名称）
 */
@Schema(description = "管理后台 - 客户版本销售授权价 Response VO")
@Data
public class ZcCustomerVersionSpecPriceRespVO {

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "客户编号")
    private Long customerId;

    @Schema(description = "产品版本编号")
    private Long versionId;

    @Schema(description = "产品版本名称")
    private String versionName;

    @Schema(description = "规格名称")
    private String spec;

    @Schema(description = "进货价（来自版本规格配置）")
    private BigDecimal inboundPrice;

    @Schema(description = "一级类销售价（来自版本规格配置）")
    private BigDecimal onePrice;

    @Schema(description = "授权销售价格")
    private BigDecimal authorizedPrice;

}
