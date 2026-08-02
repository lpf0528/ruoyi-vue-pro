package cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * 管理后台 - 成品订单-用料明细分页 Response VO
 *
 * <p>在分页列表基础上，附带当前筛选条件下全量数据的用料合计与金额合计。</p>
 */
@Schema(description = "管理后台 - 成品订单-用料明细分页 Response VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZCSalesOrderMaterialPageRespVO {

    @Schema(description = "总量", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long total;

    @Schema(description = "数据", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<ZCSalesOrderMaterialRespVO> list;

    @Schema(description = "用料合计（当前筛选条件下全量求和）", requiredMode = Schema.RequiredMode.REQUIRED, example = "320.50")
    private BigDecimal totalQuantity;

    @Schema(description = "金额合计（当前筛选条件下全量求和）", requiredMode = Schema.RequiredMode.REQUIRED, example = "15800.00")
    private BigDecimal totalAmount;

}
