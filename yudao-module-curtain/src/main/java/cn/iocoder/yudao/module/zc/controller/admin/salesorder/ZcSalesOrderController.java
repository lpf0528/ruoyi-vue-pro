package cn.iocoder.yudao.module.zc.controller.admin.salesorder;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.constraints.*;
import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZcSalesOrderDO;
import cn.iocoder.yudao.module.zc.service.salesorder.ZcSalesOrderService;

@Tag(name = "管理后台 - 销售订单")
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
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('zc:sales-order:delete')")
    public CommonResult<Boolean> deleteSalesOrder(@RequestParam("id") Long id) {
        salesOrderService.deleteSalesOrder(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除销售订单")
                @PreAuthorize("@ss.hasPermission('zc:sales-order:delete')")
    public CommonResult<Boolean> deleteSalesOrderList(@RequestParam("ids") List<Long> ids) {
        salesOrderService.deleteSalesOrderListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得销售订单")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('zc:sales-order:query')")
    public CommonResult<ZcSalesOrderRespVO> getSalesOrder(@RequestParam("id") Long id) {
        ZcSalesOrderDO salesOrder = salesOrderService.getSalesOrder(id);
        return success(BeanUtils.toBean(salesOrder, ZcSalesOrderRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得销售订单分页")
    @PreAuthorize("@ss.hasPermission('zc:sales-order:query')")
    public CommonResult<PageResult<ZcSalesOrderRespVO>> getSalesOrderPage(@Valid ZcSalesOrderPageReqVO pageReqVO) {
        PageResult<ZcSalesOrderDO> pageResult = salesOrderService.getSalesOrderPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ZcSalesOrderRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出销售订单 Excel")
    @PreAuthorize("@ss.hasPermission('zc:sales-order:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSalesOrderExcel(@Valid ZcSalesOrderPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ZcSalesOrderDO> list = salesOrderService.getSalesOrderPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "销售订单.xls", "数据", ZcSalesOrderRespVO.class,
                        BeanUtils.toBean(list, ZcSalesOrderRespVO.class));
    }

}