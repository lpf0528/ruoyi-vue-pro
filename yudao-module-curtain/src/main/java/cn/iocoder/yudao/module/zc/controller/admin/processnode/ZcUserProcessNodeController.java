package cn.iocoder.yudao.module.zc.controller.admin.processnode;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.zc.controller.admin.processnode.vo.ZcProcessNodeRespVO;
import cn.iocoder.yudao.module.zc.controller.admin.processnode.vo.ZcUserProcessNodeSaveReqVO;
import cn.iocoder.yudao.module.zc.service.processnode.ZcUserProcessNodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * 管理后台 - 员工工序节点绑定 Controller
 *
 * <p>管理员为员工分配可操作的工序节点；
 * 员工在新增工序记录前，通过 {@code /my-nodes} 接口获取自己可操作的节点列表。</p>
 *
 * @author 01Coder
 */
@Tag(name = "管理后台 - 员工工序节点绑定")
@RestController
@RequestMapping("/zc/user-process-node")
@Validated
public class ZcUserProcessNodeController {

    @Resource
    private ZcUserProcessNodeService userProcessNodeService;

    @PostMapping("/save")
    @Operation(summary = "保存员工工序节点绑定（覆盖式）",
            description = "传入 nodeIds 列表，覆盖该员工原有绑定；传空列表则清除所有绑定")
    @PreAuthorize("@ss.hasPermission('zc:user-process-node:save')")
    public CommonResult<Boolean> saveUserProcessNodes(@Valid @RequestBody ZcUserProcessNodeSaveReqVO reqVO) {
        userProcessNodeService.saveUserProcessNodes(reqVO);
        return success(true);
    }

    @GetMapping("/list")
    @Operation(summary = "获取某员工已绑定的工序节点列表（管理员用）")
    @Parameter(name = "userId", description = "员工用户 ID", required = true)
    @PreAuthorize("@ss.hasPermission('zc:user-process-node:query')")
    public CommonResult<List<ZcProcessNodeRespVO>> getUserProcessNodeList(@RequestParam("userId") Long userId) {
        return success(userProcessNodeService.getUserProcessNodeList(userId));
    }

    @GetMapping("/my-nodes")
    @Operation(summary = "获取我自己可操作的工序节点列表",
            description = "员工在新增工序记录前调用，用于填充节点下拉选项")
    @PreAuthorize("@ss.hasPermission('zc:order-process-record:create')")
    public CommonResult<List<ZcProcessNodeRespVO>> getMyProcessNodeList() {
        return success(userProcessNodeService.getMyProcessNodeList());
    }

}
