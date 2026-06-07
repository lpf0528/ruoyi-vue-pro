package cn.iocoder.yudao.module.zc.controller.admin.curtaininstallprocess.vo;

import lombok.Data;
import lombok.experimental.Accessors;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "管理后台 - 安装工艺精简 Response VO")
@Data
@Accessors(chain = true)
public class ZcCurtainInstallProcessSimpleRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "工艺名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "关联工序节点 ID 列表")
    private List<Long> nodeIds;

}
