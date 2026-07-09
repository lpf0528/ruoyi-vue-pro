package cn.iocoder.yudao.module.zc.controller.admin.warehouse.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Schema(description = "管理后台 - 仓库列表 Request VO")
@Data
@Accessors(chain = true)
public class ZcWarehouseListReqVO {

    @Schema(description = "仓库名称")
    private String name;

}
