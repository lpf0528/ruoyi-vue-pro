package cn.iocoder.yudao.module.zc.controller.admin.productversion.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * 客户版本销售授权价 - 批量保存请求 VO
 *
 * <p>id 字段无需传入，服务端忽略。保存前会物理删除该客户所有旧记录，再全量插入。</p>
 */
@Schema(description = "管理后台 - 客户版本销售授权价批量保存 Request VO")
@Data
public class ZcCustomerVersionSpecPriceSaveReqVO {

    @Schema(description = "客户编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "8396")
    @NotNull(message = "客户编号不能为空")
    private Long customerId;

    @Schema(description = "规格授权价明细列表")
    @Valid
    private List<SpecPriceItem> specPrices;

    @Schema(description = "规格授权价明细")
    @Data
    public static class SpecPriceItem {

        @Schema(description = "产品版本编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "9553")
        @NotNull(message = "产品版本编号不能为空")
        private Long versionId;

        @Schema(description = "规格名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "2.8")
        @NotNull(message = "规格不能为空")
        private String spec;

        @Schema(description = "授权销售价格", requiredMode = Schema.RequiredMode.REQUIRED, example = "12.00")
        @NotNull(message = "授权价格不能为空")
        private BigDecimal authorizedPrice;

    }

}
