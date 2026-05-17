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
import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZcSalesOrderCurtainDO;
import cn.iocoder.yudao.module.zc.service.salesorder.ZcSalesOrderCurtainService;

@Tag(name = "管理后台 - 成品订单-窗帘行")
@RestController
@RequestMapping("/zc/sales-order-curtain")
@Validated
public class ZcSalesOrderCurtainController {

    @Resource
    private ZcSalesOrderCurtainService salesOrderCurtainService;

    @PostMapping("/create")
    @Operation(summary = "创建成品订单-窗帘行")
    @PreAuthorize("@ss.hasPermission('zc:sales-order-curtain:create')")
    public CommonResult<Long> createSalesOrderCurtain(@Valid @RequestBody ZcSalesOrderCurtainSaveReqVO createReqVO) {
        return success(salesOrderCurtainService.createSalesOrderCurtain(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新成品订单-窗帘行")
    @PreAuthorize("@ss.hasPermission('zc:sales-order-curtain:update')")
    public CommonResult<Boolean> updateSalesOrderCurtain(@Valid @RequestBody ZcSalesOrderCurtainSaveReqVO updateReqVO) {
        salesOrderCurtainService.updateSalesOrderCurtain(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除成品订单-窗帘行")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('zc:sales-order-curtain:delete')")
    public CommonResult<Boolean> deleteSalesOrderCurtain(@RequestParam("id") Long id) {
        salesOrderCurtainService.deleteSalesOrderCurtain(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除成品订单-窗帘行")
                @PreAuthorize("@ss.hasPermission('zc:sales-order-curtain:delete')")
    public CommonResult<Boolean> deleteSalesOrderCurtainList(@RequestParam("ids") List<Long> ids) {
        salesOrderCurtainService.deleteSalesOrderCurtainListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得成品订单-窗帘行")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('zc:sales-order-curtain:query')")
    public CommonResult<ZcSalesOrderCurtainRespVO> getSalesOrderCurtain(@RequestParam("id") Long id) {
        ZcSalesOrderCurtainDO salesOrderCurtain = salesOrderCurtainService.getSalesOrderCurtain(id);
        return success(BeanUtils.toBean(salesOrderCurtain, ZcSalesOrderCurtainRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得成品订单-窗帘行分页")
    @PreAuthorize("@ss.hasPermission('zc:sales-order-curtain:query')")
    public CommonResult<PageResult<ZcSalesOrderCurtainRespVO>> getSalesOrderCurtainPage(@Valid ZcSalesOrderCurtainPageReqVO pageReqVO) {
        PageResult<ZcSalesOrderCurtainDO> pageResult = salesOrderCurtainService.getSalesOrderCurtainPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ZcSalesOrderCurtainRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出成品订单-窗帘行 Excel")
    @PreAuthorize("@ss.hasPermission('zc:sales-order-curtain:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSalesOrderCurtainExcel(@Valid ZcSalesOrderCurtainPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ZcSalesOrderCurtainDO> list = salesOrderCurtainService.getSalesOrderCurtainPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "成品订单-窗帘行.xls", "数据", ZcSalesOrderCurtainRespVO.class,
                        BeanUtils.toBean(list, ZcSalesOrderCurtainRespVO.class));
    }

}