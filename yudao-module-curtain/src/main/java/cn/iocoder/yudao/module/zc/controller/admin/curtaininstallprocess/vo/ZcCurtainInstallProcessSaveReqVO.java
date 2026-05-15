package cn.iocoder.yudao.module.zc.controller.admin.curtaininstallprocess.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 安装工艺新增/修改 Request VO")
@Data
public class ZcCurtainInstallProcessSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "1614")
    private Long id;

    @Schema(description = "工艺名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @NotEmpty(message = "工艺名称不能为空")
    private String name;

    @Schema(description = "备注")
    private String note;

}