package cn.iocoder.yudao.module.zc.controller.admin.workshopuser.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 管理后台 - 车间员工精简 Response VO
 *
 * <p>仅包含前端下拉选项所需的最小字段集，避免返回冗余数据</p>
 *
 * @author 01Coder
 */
@Schema(description = "管理后台 - 车间员工精简 Response VO")
@Data
@Accessors(chain = true)
public class ZcWorkshopUserSimpleRespVO {

    /** 主键 */
    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    /** 员工姓名 */
    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

}
