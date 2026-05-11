package cn.iocoder.yudao.module.zc.controller.admin.sales;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.zc.controller.admin.vo.sales.ZcSalesOrderPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.sales.ZcSalesOrderRespVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.sales.ZcSalesOrderSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.sale.ZcSalesOrderDO;
import cn.iocoder.yudao.module.zc.service.sales.ZcSalesOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 智仓销售订单")
@RestController
@RequestMapping("/zc/sales-order")
@Validated
public class ZcSalesOrderController {

    @Resource
    private ZcSalesOrderService salesOrderService;

    @PostMapping("/create")
    @Operation(summary = "创建销售订单")
    @PreAuthorize("@ss.hasPermission('zc:sales-order:create')")
    public CommonResult<Long> createSalesOrder(@Valid @RequestBody ZcSalesOrderSaveReqVO createReqVO) {
        return success(salesOrderService.createSalesOrder(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新销售订单")
    @PreAuthorize("@ss.hasPermission('zc:sales-order:update')")
    public CommonResult<Boolean> updateSalesOrder(@Valid @RequestBody ZcSalesOrderSaveReqVO updateReqVO) {
        salesOrderService.updateSalesOrder(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除销售订单")
    @PreAuthorize("@ss.hasPermission('zc:sales-order:delete')")
    public CommonResult<Boolean> deleteSalesOrder(@RequestParam("id") Long id) {
        salesOrderService.deleteSalesOrder(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得销售订单详情")
    @PreAuthorize("@ss.hasPermission('zc:sales-order:query')")
    public CommonResult<ZcSalesOrderRespVO> getSalesOrder(@RequestParam("id") Long id) {
        return success(salesOrderService.getSalesOrder(id));
    }

    @GetMapping("/page")
    @Operation(summary = "销售订单分页")
    @PreAuthorize("@ss.hasPermission('zc:sales-order:query')")
    public CommonResult<PageResult<ZcSalesOrderRespVO>> getSalesOrderPage(@Valid ZcSalesOrderPageReqVO pageReqVO) {
        PageResult<ZcSalesOrderDO> pageResult = salesOrderService.getSalesOrderPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ZcSalesOrderRespVO.class));
    }

    @PutMapping("/confirm")
    @Operation(summary = "确认订单")
    @PreAuthorize("@ss.hasPermission('zc:sales-order:confirm')")
    public CommonResult<Boolean> confirmSalesOrder(@RequestParam("id") Long id) {
        salesOrderService.confirmSalesOrder(id);
        return success(true);
    }

    @PutMapping("/cancel-confirm")
    @Operation(summary = "取消确认订单")
    @PreAuthorize("@ss.hasPermission('zc:sales-order:confirm')")
    public CommonResult<Boolean> cancelConfirmSalesOrder(@RequestParam("id") Long id) {
        salesOrderService.cancelConfirmSalesOrder(id);
        return success(true);
    }

}
