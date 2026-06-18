package cn.iocoder.yudao.module.zc.controller.admin.bills.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 管理后台 - 收款方式精简 Response VO
 *
 * <p>主要用于前端下拉选项及列表展示</p>
 */
@Schema(description = "管理后台 - 收款方式精简 Response VO")
@Data
@Accessors(chain = true)
public class ZcBillMethodsSimpleRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "分组：0=系统配置，1=手工配置", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer group;

}
