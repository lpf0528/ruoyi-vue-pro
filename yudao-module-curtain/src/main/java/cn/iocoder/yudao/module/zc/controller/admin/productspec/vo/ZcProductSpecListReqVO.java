package cn.iocoder.yudao.module.zc.controller.admin.productspec.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Schema(description = "管理后台 - 产品规格列表 Request VO")
@Data
@Accessors(chain = true)
public class ZcProductSpecListReqVO {

    @Schema(description = "规格值")
    private String value;

}