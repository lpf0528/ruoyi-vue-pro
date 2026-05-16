package cn.iocoder.yudao.module.zc.controller.admin.curtain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Schema(description = "管理后台 - 窗帘列表 Request VO")
@Data
@Accessors(chain = true)
public class ZcCurtainListReqVO {

    @Schema(description = "款式名称")
    private String name;

}
