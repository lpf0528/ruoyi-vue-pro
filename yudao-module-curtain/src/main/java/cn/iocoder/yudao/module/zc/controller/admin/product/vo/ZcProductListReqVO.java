package cn.iocoder.yudao.module.zc.controller.admin.product.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Schema(description = "管理后台 - 产品档案列表 Request VO")
@Data
@Accessors(chain = true)
public class ZcProductListReqVO {

    @Schema(description = "产品名称", example = "遮光布")
    private String name;

}
