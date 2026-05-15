package cn.iocoder.yudao.module.zc.controller.admin.logistics.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Schema(description = "管理后台 - 物流公司列表 Request VO")
@Data
@Accessors(chain = true)
public class ZcLogisticsListReqVO {

    @Schema(description = "名称")
    private String name;

}
