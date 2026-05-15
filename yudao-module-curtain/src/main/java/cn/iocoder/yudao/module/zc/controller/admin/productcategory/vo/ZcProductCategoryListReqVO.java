package cn.iocoder.yudao.module.zc.controller.admin.productcategory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Schema(description = "管理后台 - 产品类别列表 Request VO")
@Data
@Accessors(chain = true)
public class ZcProductCategoryListReqVO {

    @Schema(description = "类别名称")
    private String value;

}
