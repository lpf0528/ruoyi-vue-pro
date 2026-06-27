package cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 管理后台 - 已确认订单用料按产品规格统计 Response VO
 */
@Schema(description = "管理后台 - 已确认订单用料按产品规格统计 Response VO")
@Data
public class ZcSalesOrderMaterialProductStatisticsRespVO {

    @Schema(description = "产品编号（货号）", example = "24015")
    private Long productId;

    @Schema(description = "产品名称", example = "遮光布 A 款")
    private String productName;

    @Schema(description = "规格", example = "2.8m")
    private String spec;

    @Schema(description = "用料数量合计", example = "156.50")
    private BigDecimal quantity;

    @Schema(description = "单位", example = "米")
    private String unitValue;

}
