package cn.iocoder.yudao.module.zc.controller.admin.progress;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.zc.controller.admin.vo.progress.ZcProductionQueueUpdateReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.progress.ZcSalesOrderProductionQueueDO;
import cn.iocoder.yudao.module.zc.service.progress.ZcOrderProgressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 智仓生产队列")
@RestController
@RequestMapping("/zc/production-queue")
@Validated
public class ZcProductionQueueController {

    @Resource
    private ZcOrderProgressService orderProgressService;

    @GetMapping("/list")
    @Operation(summary = "按订单列出生产队列")
    @PreAuthorize("@ss.hasPermission('zc:production-queue:query')")
    public CommonResult<List<ZcSalesOrderProductionQueueDO>> list(@RequestParam("orderId") Long orderId) {
        return success(orderProgressService.listProductionQueue(orderId));
    }

    @PutMapping("/update")
    @Operation(summary = "更新队列状态（推进工序）")
    @PreAuthorize("@ss.hasPermission('zc:production-queue:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody ZcProductionQueueUpdateReqVO reqVO) {
        orderProgressService.updateProductionQueue(reqVO);
        return success(true);
    }

}
