package cn.iocoder.yudao.module.zc.controller.admin.inventoryrecord;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.constraints.*;
import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.zc.controller.admin.inventoryrecord.vo.*;
import cn.iocoder.yudao.module.zc.service.inventoryrecord.ZcInventoryRecordService;

@Tag(name = "管理后台 - 盘点记录")
@RestController
@RequestMapping("/zc/inventory-record")
@Validated
public class ZcInventoryRecordController {

    @Resource
    private ZcInventoryRecordService inventoryRecordService;

    @PostMapping("/create")
    @Operation(summary = "创建盘点记录")
    @PreAuthorize("@ss.hasPermission('zc:inventory-record:create')")
    public CommonResult<Long> createInventoryRecord(@Valid @RequestBody ZcInventoryRecordSaveReqVO createReqVO) {
        return success(inventoryRecordService.createInventoryRecord(createReqVO));
    }

    @GetMapping("/page")
    @Operation(summary = "获得盘点记录分页")
    @PreAuthorize("@ss.hasPermission('zc:inventory-record:query')")
    public CommonResult<PageResult<ZcInventoryRecordRespVO>> getInventoryRecordPage(@Valid ZcInventoryRecordPageReqVO pageReqVO) {
        return success(inventoryRecordService.getInventoryRecordPage(pageReqVO));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出盘点记录 Excel")
    @PreAuthorize("@ss.hasPermission('zc:inventory-record:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportInventoryRecordExcel(@Valid ZcInventoryRecordPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ZcInventoryRecordRespVO> list = inventoryRecordService.getInventoryRecordPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "盘点记录.xls", "数据", ZcInventoryRecordRespVO.class, list);
    }

}