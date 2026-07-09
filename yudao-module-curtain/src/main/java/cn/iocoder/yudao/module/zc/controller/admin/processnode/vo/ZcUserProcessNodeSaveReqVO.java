package cn.iocoder.yudao.module.zc.controller.admin.processnode.vo;

import cn.iocoder.yudao.module.system.framework.operatelog.core.AdminUserParseFunction;
import com.mzt.logapi.starter.annotation.DiffLogField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * 管理后台 - 员工工序节点绑定 Request VO
 *
 * <p>覆盖式保存：传入 nodeIds 后，会清空该员工原有绑定关系，重新写入新的绑定。
 * 传入空列表则清除该员工所有绑定。</p>
 */
@Schema(description = "管理后台 - 员工工序节点绑定 Request VO")
@Data
public class ZcUserProcessNodeSaveReqVO {

    @Schema(description = "员工用户 ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    @DiffLogField(name = "员工", function = AdminUserParseFunction.NAME)
    @NotNull(message = "员工用户 ID 不能为空")
    private Long userId;

    @Schema(description = "绑定的工序节点 ID 列表（覆盖式，传空则清除所有绑定）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "节点列表不能为空")
    private List<Long> nodeIds;

}
