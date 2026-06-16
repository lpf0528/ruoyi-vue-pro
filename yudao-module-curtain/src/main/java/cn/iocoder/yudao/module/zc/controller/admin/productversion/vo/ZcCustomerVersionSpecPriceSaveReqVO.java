package cn.iocoder.yudao.module.zc.controller.admin.productversion.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 客户版本销售授权价 - 单条保存请求 VO
 *
 * <p>批量保存时传入该对象的列表，每条记录对应一个客户+版本+规格的授权价格。</p>
 */
@Schema(description = "管理后台 - 客户版本销售授权价保存 Request VO")
@Data
public class ZcCustomerVersionSpecPriceSaveReqVO {

    @Schema(description = "客户编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "8396")
    @NotNull(message = "客户编号不能为空")
    private Long customerId;

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
