package cn.iocoder.yudao.module.zc.controller.admin.processnode.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 管理后台 - 工序节点配置精简 Response VO
 *
 * <p>主要用于前端的下拉选项，仅返回 id 与名称</p>
 */
@Schema(description = "管理后台 - 工序节点配置精简 Response VO")
@Data
@Accessors(chain = true)
public class ZcProcessNodeSimpleRespVO {

    /** 主键 */
    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    /** 工序名称，如：备料、裁剪、缝制、定型、质检、包装 */
    @Schema(description = "工序名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "裁剪")
    private String name;

}
