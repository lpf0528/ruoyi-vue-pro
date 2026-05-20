package cn.iocoder.yudao.module.zc.controller.admin.bills;

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

import cn.iocoder.yudao.module.zc.controller.admin.bills.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.bills.ZcBillsDO;
import cn.iocoder.yudao.module.zc.service.bills.ZcBillsService;

@Tag(name = "管理后台 - 收支账单")
@RestController
@RequestMapping("/zc/bills")
@Validated
public class ZcBillsController {

    @Resource
    private ZcBillsService billsService;

    @PostMapping("/create")
    @Operation(summary = "创建收支账单")
    @PreAuthorize("@ss.hasPermission('zc:bills:create')")
    public CommonResult<Long> createBills(@Valid @RequestBody ZcBillsSaveReqVO createReqVO) {
        return success(billsService.createBills(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新收支账单")
    @PreAuthorize("@ss.hasPermission('zc:bills:update')")
    public CommonResult<Boolean> updateBills(@Valid @RequestBody ZcBillsSaveReqVO updateReqVO) {
        billsService.updateBills(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除收支账单")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('zc:bills:delete')")
    public CommonResult<Boolean> deleteBills(@RequestParam("id") Long id) {
        billsService.deleteBills(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除收支账单")
                @PreAuthorize("@ss.hasPermission('zc:bills:delete')")
    public CommonResult<Boolean> deleteBillsList(@RequestParam("ids") List<Long> ids) {
        billsService.deleteBillsListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得收支账单")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('zc:bills:query')")
    public CommonResult<ZcBillsRespVO> getBills(@RequestParam("id") Long id) {
        ZcBillsDO bills = billsService.getBills(id);
        return success(BeanUtils.toBean(bills, ZcBillsRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得收支账单分页")
    @PreAuthorize("@ss.hasPermission('zc:bills:query')")
    public CommonResult<PageResult<ZcBillsRespVO>> getBillsPage(@Valid ZcBillsPageReqVO pageReqVO) {
        PageResult<ZcBillsDO> pageResult = billsService.getBillsPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ZcBillsRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出收支账单 Excel")
    @PreAuthorize("@ss.hasPermission('zc:bills:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportBillsExcel(@Valid ZcBillsPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ZcBillsDO> list = billsService.getBillsPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "收支账单.xls", "数据", ZcBillsRespVO.class,
                        BeanUtils.toBean(list, ZcBillsRespVO.class));
    }

}