package cn.iocoder.yudao.module.zc.controller.admin.curtaininstallprocess.vo;

import lombok.Data;
import lombok.experimental.Accessors;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "管理后台 - 安装工艺列表 Request VO")
@Data
@Accessors(chain = true)
public class ZcCurtainInstallProcessListReqVO {

    @Schema(description = "工艺名称")
    private String name;

}
