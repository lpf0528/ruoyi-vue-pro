package cn.iocoder.yudao.module.zc.controller.admin.productcategory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Schema(description = "管理后台 - 产品类别精简 Response VO")
@Data
@Accessors(chain = true)
public class ZcProductCategorySimpleRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "10243")
    private Long id;

    @Schema(description = "类别名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String value;

}
