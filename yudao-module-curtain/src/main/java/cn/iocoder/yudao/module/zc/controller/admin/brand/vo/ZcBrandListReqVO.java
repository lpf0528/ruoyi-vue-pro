package cn.iocoder.yudao.module.zc.controller.admin.brand.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Schema(description = "管理后台 - 品牌列表 Request VO")
@Data
@Accessors(chain = true)
public class ZcBrandListReqVO {

    @Schema(description = "名称")
    private String name;

}
