package cn.iocoder.yudao.module.zc.controller.admin.processnode;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.zc.controller.admin.processnode.vo.ZcOrderProcessRecordCompleteReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.processnode.vo.ZcOrderProcessRecordRespVO;
import cn.iocoder.yudao.module.zc.controller.admin.processnode.vo.ZcOrderProcessRecordSaveReqVO;
import cn.iocoder.yudao.module.zc.service.processnode.ZcOrderProcessRecordService;
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
 * 管理后台 - 订单工序记录 Controller
 *
 * <p>工厂员工通过此接口推进订单的加工进度，
 * 管理员和客户通过 {@code /list} 接口查看订单工序时间线。</p>
 *
 * @author 01Coder
 */
@Tag(name = "管理后台 - 订单工序记录")
@RestController
@RequestMapping("/zc/order-process-record")
@Validated
public class ZcOrderProcessRecordController {

    @Resource
    private ZcOrderProcessRecordService processRecordService;

    @PostMapping("/create")
    @Operation(summary = "新增工序记录（开始某道工序）",
            description = "员工只能选择自己已绑定的节点；订单必须处于待生产或生产中状态")
    @PreAuthorize("@ss.hasPermission('zc:order-process-record:create')")
    public CommonResult<Long> createProcessRecord(@Valid @RequestBody ZcOrderProcessRecordSaveReqVO reqVO) {
        return success(processRecordService.createProcessRecord(reqVO));
    }

    @PutMapping("/complete")
    @Operation(summary = "标记工序完成")
    @PreAuthorize("@ss.hasPermission('zc:order-process-record:update')")
    public CommonResult<Boolean> completeProcessRecord(@Valid @RequestBody ZcOrderProcessRecordCompleteReqVO reqVO) {
        processRecordService.completeProcessRecord(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除工序记录（仅允许删除进行中的记录）")
    @Parameter(name = "id", description = "记录 ID", required = true)
    @PreAuthorize("@ss.hasPermission('zc:order-process-record:delete')")
    public CommonResult<Boolean> deleteProcessRecord(@RequestParam("id") Long id) {
        processRecordService.deleteProcessRecord(id);
        return success(true);
    }

    @GetMapping("/list")
    @Operation(summary = "获取订单工序时间线（按时间升序）")
    @Parameter(name = "orderId", description = "订单 ID", required = true)
    @PreAuthorize("@ss.hasPermission('zc:order-process-record:query')")
    public CommonResult<List<ZcOrderProcessRecordRespVO>> getProcessRecordList(
            @RequestParam("orderId") Long orderId) {
        return success(processRecordService.getProcessRecordList(orderId));
    }

}
