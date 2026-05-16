package cn.iocoder.yudao.module.zc.controller.admin.product.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Schema(description = "管理后台 - 产品列表 Request VO")
@Data
@Accessors(chain = true)
public class ZcProductListReqVO {

    @Schema(description = "名称", example = "张三")
    private String name;

}
