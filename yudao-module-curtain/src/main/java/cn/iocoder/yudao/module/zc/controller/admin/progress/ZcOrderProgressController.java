package cn.iocoder.yudao.module.zc.controller.admin.progress;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.zc.controller.admin.vo.progress.ZcOrderProgressAppendReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.progress.ZcOrderProgressLogPageReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.progress.ZcSalesOrderProgressLogDO;
import cn.iocoder.yudao.module.zc.service.progress.ZcOrderProgressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 智仓订单进度日志")
@RestController
@RequestMapping("/zc/order-progress")
@Validated
public class ZcOrderProgressController {

    @Resource
    private ZcOrderProgressService orderProgressService;

    @PostMapping("/append")
    @Operation(summary = "追加订单进度日志")
    @PreAuthorize("@ss.hasPermission('zc:order-progress:create')")
    public CommonResult<Boolean> append(@Valid @RequestBody ZcOrderProgressAppendReqVO reqVO) {
        orderProgressService.appendLog(reqVO);
        return success(true);
    }

    @GetMapping("/log-page")
    @Operation(summary = "订单进度日志分页")
    @PreAuthorize("@ss.hasPermission('zc:order-progress:query')")
    public CommonResult<PageResult<ZcSalesOrderProgressLogDO>> logPage(@Valid ZcOrderProgressLogPageReqVO pageReqVO) {
        return success(orderProgressService.getProgressLogPage(pageReqVO));
    }

}
