package cn.iocoder.yudao.module.zc.controller.admin.orderoperationlog;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.Valid;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.module.zc.controller.admin.orderoperationlog.vo.ZcOrderOperationLogPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.orderoperationlog.vo.ZcOrderOperationLogRespVO;
import cn.iocoder.yudao.module.zc.service.orderoperationlog.ZcOrderOperationLogService;

/**
 * 管理后台 - 销售订单操作记录 Controller
 */
@Tag(name = "管理后台 - 销售订单操作记录")
@RestController
@RequestMapping("/zc/order-operation-log")
@Validated
public class ZcOrderOperationLogController {

    @Resource
    private ZcOrderOperationLogService orderOperationLogService;

    @GetMapping("/page")
    @Operation(summary = "获得订单操作记录分页（按订单 ID 查询，可按对象类型、操作类型等过滤）")
    @PreAuthorize("@ss.hasPermission('zc:sales-order:query')")
    public CommonResult<PageResult<ZcOrderOperationLogRespVO>> getLogPage(
            @Valid ZcOrderOperationLogPageReqVO pageReqVO) {
        return success(orderOperationLogService.getLogPage(pageReqVO));
    }

}
