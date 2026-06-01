package cn.iocoder.yudao.module.zc.controller.admin.workshopuser.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 管理后台 - 车间员工列表 Request VO
 *
 * <p>主要用于前端下拉选项等精简列表场景</p>
 *
 * @author 01Coder
 */
@Schema(description = "管理后台 - 车间员工列表 Request VO")
@Data
@Accessors(chain = true)
public class ZcWorkshopUserListReqVO {

    /** 状态，参见 CommonStatusEnum；0=禁用，1=启用 */
    @Schema(description = "状态", example = "1")
    private Integer status;

}
