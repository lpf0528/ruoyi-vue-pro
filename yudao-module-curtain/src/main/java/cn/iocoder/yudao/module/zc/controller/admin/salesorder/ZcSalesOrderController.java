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
import cn.iocoder.yudao.module.zc.service.salesorder.ZCSalesOrderMaterialService;
import cn.iocoder.yudao.module.zc.service.salesorder.ZcSalesOrderService;

@Tag(name = "管理后台 - 销售订单")
@RestController
@RequestMapping("/zc/sales-order")
@Validated
public class ZcSalesOrderController {

    @Resource
    private ZcSalesOrderService salesOrderService;
    @Resource
    private ZCSalesOrderMaterialService salesOrderMaterialService;

    @PostMapping("/create")
    @Operation(summary = "创建销售订单（整单，含窗帘行→结构行→用料明细）")
    @PreAuthorize("@ss.hasPermission('zc:sales-order:create')")
    public CommonResult<Long> createSalesOrder(@Valid @RequestBody ZcSalesOrderCreateReqVO createReqVO) {
        return success(salesOrderService.createSalesOrder(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "整单更新销售订单（含窗帘行→结构行→用料明细，已确认订单禁止修改）")
    @PreAuthorize("@ss.hasPermission('zc:sales-order:update')")
    public CommonResult<Boolean> updateSalesOrder(@Valid @RequestBody ZcSalesOrderUpdateReqVO updateReqVO) {
        salesOrderService.updateSalesOrder(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除销售订单（已确认的订单禁止删除）")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('zc:sales-order:delete')")
    public CommonResult<Boolean> deleteSalesOrder(@RequestParam("id") Long id) {
        salesOrderService.deleteSalesOrder(id);
        return success(true);
    }

//    @DeleteMapping("/delete-list")
//    @Parameter(name = "ids", description = "编号", required = true)
//    @Operation(summary = "批量删除销售订单")
//                @PreAuthorize("@ss.hasPermission('zc:sales-order:delete')")
//    public CommonResult<Boolean> deleteSalesOrderList(@RequestParam("ids") List<Long> ids) {
//        salesOrderService.deleteSalesOrderListByIds(ids);
//        return success(true);
//    }

    @GetMapping("/get")
    @Operation(summary = "获得销售订单")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('zc:sales-order:query')")
    public CommonResult<ZcSalesOrderRespVO> getSalesOrder(@RequestParam("id") Long id) {
        ZcSalesOrderDO salesOrder = salesOrderService.getSalesOrder(id);
        return success(BeanUtils.toBean(salesOrder, ZcSalesOrderRespVO.class));
    }

    @GetMapping("/detail")
    @Operation(summary = "获得销售订单完整详情（主表信息 + 窗帘→结构→用料 三层嵌套）")
    @Parameter(name = "id", description = "销售订单编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('zc:sales-order:query')")
    public CommonResult<ZcSalesOrderDetailRespVO> getSalesOrderDetail(
            @RequestParam("id") Long id) {
        return success(salesOrderService.getSalesOrderDetail(id));
    }

    @GetMapping("/page")
    @Operation(summary = "获得销售订单分页")
    @PreAuthorize("@ss.hasPermission('zc:sales-order:query')")
    public CommonResult<PageResult<ZcSalesOrderRespVO>> getSalesOrderPage(@Valid ZcSalesOrderPageReqVO pageReqVO) {
        return success(salesOrderService.getSalesOrderPage(pageReqVO));
    }

    @GetMapping("/app/page")
    @Operation(summary = "获得销售订单分页（已确认，自动过滤 status = unconfirmed 的订单）")
    @PreAuthorize("@ss.hasPermission('zc:sales-order:query')")
    public CommonResult<PageResult<ZcSalesOrderRespVO>> getSalesOrderAppPage(@Valid ZcSalesOrderPageReqVO pageReqVO) {
        // 固定过滤未确认订单，前端无需传此参数
        pageReqVO.setIncludeUnconfirmed(false);
        return success(salesOrderService.getSalesOrderPage(pageReqVO));
    }

    @PutMapping("/expedited")
    @Operation(summary = "标记销售订单为加急")
    @Parameter(name = "orderId", description = "销售订单 ID", required = true)
    @PreAuthorize("@ss.hasPermission('zc:sales-order:update')")
    public CommonResult<Boolean> markExpedited(@RequestParam("orderId") Long orderId) {
        salesOrderService.markExpedited(orderId);
        return success(true);
    }

    @PutMapping("/confirm")
    @Operation(summary = "确认销售订单（状态 unconfirmed → confirmed，扣减客户余额）")
    @Parameter(name = "id", description = "销售订单 ID", required = true)
    @PreAuthorize("@ss.hasPermission('zc:sales-order:update')")
    public CommonResult<Boolean> confirmSalesOrder(@RequestParam("id") Long id) {
        salesOrderService.confirmSalesOrder(id);
        return success(true);
    }

    @PutMapping("/cancel-confirm")
    @Operation(summary = "取消确认销售订单（状态 confirmed → unconfirmed，退回客户余额）")
    @Parameter(name = "id", description = "销售订单 ID", required = true)
    @PreAuthorize("@ss.hasPermission('zc:sales-order:update')")
    public CommonResult<Boolean> cancelConfirmSalesOrder(@RequestParam("id") Long id) {
        salesOrderService.cancelConfirmSalesOrder(id);
        return success(true);
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出销售订单 Excel")
    @PreAuthorize("@ss.hasPermission('zc:sales-order:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSalesOrderExcel(@Valid ZcSalesOrderPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ZcSalesOrderRespVO> list = salesOrderService.getSalesOrderPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "销售订单.xls", "数据", ZcSalesOrderRespVO.class, list);
    }

    @PutMapping("/cut-material")
    @Operation(summary = "成品订单裁剪（绑定批次、记录裁剪数量、扣减批次库存）")
    @PreAuthorize("@ss.hasPermission('zc:sales-order:update')")
    public CommonResult<Boolean> cutMaterial(@RequestBody @Valid ZcCutMaterialReqVO reqVO) {
        salesOrderMaterialService.cutMaterial(reqVO);
        return success(true);
    }

    @PutMapping("/cancel-cut-material")
    @Operation(summary = "撤销裁剪（回退批次库存、清空配料绑定、写入撤销裁剪记录）")
    @Parameter(name = "materialId", description = "用料明细ID", required = true)
    @PreAuthorize("@ss.hasPermission('zc:sales-order:update')")
    public CommonResult<Boolean> cancelCutMaterial(@RequestParam("materialId") Long materialId) {
        salesOrderMaterialService.cancelCutMaterial(materialId);
        return success(true);
    }

    @GetMapping("/export-pdf")
    @Operation(summary = "导出销售订单 PDF（含全量明细：窗帘行→结构行→用料明细）")
    @Parameter(name = "id", description = "销售订单 ID", required = true)
    @PreAuthorize("@ss.hasPermission('zc:sales-order:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSalesOrderPdf(@RequestParam("id") Long id,
                                    HttpServletResponse response) throws IOException {
        byte[] pdfBytes = salesOrderService.generateSalesOrderPdf(id);
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition",
            "attachment; filename=\"sales-order-" + id + ".pdf\"; filename*=UTF-8''sales-order-" + id + ".pdf\"");
        response.setContentLength(pdfBytes.length);
        response.getOutputStream().write(pdfBytes);
        response.getOutputStream().flush();
    }

}