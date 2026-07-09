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
import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZcSalesOrderStructureDO;
import cn.iocoder.yudao.module.zc.service.salesorder.ZcSalesOrderStructureService;

@Tag(name = "管理后台 - 成品订单-结构")
@RestController
@RequestMapping("/zc/sales-order-structure")
@Validated
public class ZcSalesOrderStructureController {

    @Resource
    private ZcSalesOrderStructureService salesOrderStructureService;

    @PostMapping("/create")
    @Operation(summary = "创建成品订单-结构")
    @PreAuthorize("@ss.hasPermission('zc:sales-order-structure:create')")
    public CommonResult<Long> createSalesOrderStructure(@Valid @RequestBody ZcSalesOrderStructureSaveReqVO createReqVO) {
        return success(salesOrderStructureService.createSalesOrderStructure(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新成品订单-结构")
    @PreAuthorize("@ss.hasPermission('zc:sales-order-structure:update')")
    public CommonResult<Boolean> updateSalesOrderStructure(@Valid @RequestBody ZcSalesOrderStructureSaveReqVO updateReqVO) {
        salesOrderStructureService.updateSalesOrderStructure(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除成品订单-结构")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('zc:sales-order-structure:delete')")
    public CommonResult<Boolean> deleteSalesOrderStructure(@RequestParam("id") Long id) {
        salesOrderStructureService.deleteSalesOrderStructure(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除成品订单-结构")
                @PreAuthorize("@ss.hasPermission('zc:sales-order-structure:delete')")
    public CommonResult<Boolean> deleteSalesOrderStructureList(@RequestParam("ids") List<Long> ids) {
        salesOrderStructureService.deleteSalesOrderStructureListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得成品订单-结构")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('zc:sales-order-structure:query')")
    public CommonResult<ZcSalesOrderStructureRespVO> getSalesOrderStructure(@RequestParam("id") Long id) {
        ZcSalesOrderStructureDO salesOrderStructure = salesOrderStructureService.getSalesOrderStructure(id);
        return success(BeanUtils.toBean(salesOrderStructure, ZcSalesOrderStructureRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得成品订单-结构分页")
    @PreAuthorize("@ss.hasPermission('zc:sales-order-structure:query')")
    public CommonResult<PageResult<ZcSalesOrderStructureRespVO>> getSalesOrderStructurePage(@Valid ZcSalesOrderStructurePageReqVO pageReqVO) {
        PageResult<ZcSalesOrderStructureDO> pageResult = salesOrderStructureService.getSalesOrderStructurePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ZcSalesOrderStructureRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出成品订单-结构 Excel")
    @PreAuthorize("@ss.hasPermission('zc:sales-order-structure:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSalesOrderStructureExcel(@Valid ZcSalesOrderStructurePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ZcSalesOrderStructureDO> list = salesOrderStructureService.getSalesOrderStructurePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "成品订单-结构.xls", "数据", ZcSalesOrderStructureRespVO.class,
                        BeanUtils.toBean(list, ZcSalesOrderStructureRespVO.class));
    }

}