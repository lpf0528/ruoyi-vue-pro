package cn.iocoder.yudao.module.zc.controller.admin.customer.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Schema(description = "管理后台 - 客户资料精简 Response VO")
@Data
@Accessors(chain = true)
public class ZcCustomerSimpleRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "简称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String shortName;

}
