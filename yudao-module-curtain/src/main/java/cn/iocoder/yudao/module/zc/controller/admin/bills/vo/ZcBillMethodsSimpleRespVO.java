package cn.iocoder.yudao.module.zc.controller.admin.bills.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 管理后台 - 收款方式精简 Response VO
 *
 * <p>仅用于前端下拉选项，只返回 id 和 name，避免暴露多余字段</p>
 */
@Schema(description = "管理后台 - 收款方式精简 Response VO")
@Data
@Accessors(chain = true)
public class ZcBillMethodsSimpleRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

}
