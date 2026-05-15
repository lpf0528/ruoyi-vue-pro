package cn.iocoder.yudao.module.zc.controller.admin.supplier.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Schema(description = "管理后台 - 供应商列表 Request VO")
@Data
@Accessors(chain = true)
public class ZcSupplierListReqVO {

    @Schema(description = "简称")
    private String shortName;

    @Schema(description = "全称")
    private String name;

}
