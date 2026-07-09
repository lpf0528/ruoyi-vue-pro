package cn.iocoder.yudao.module.zc.controller.admin.customerbalancelog;

import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.*;
import jakarta.servlet.http.*;
import java.util.*;
import java.io.IOException;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.zc.controller.admin.customerbalancelog.vo.*;
import cn.iocoder.yudao.module.zc.service.customerbalancelog.ZcCustomerBalanceLogService;

@Tag(name = "管理后台 - 客户余额变动流水")
@RestController
@RequestMapping("/zc/customer-balance-log")
@Validated
public class ZcCustomerBalanceLogController {

    @Resource
    private ZcCustomerBalanceLogService customerBalanceLogService;

    @GetMapping("/page")
    @Operation(summary = "获得客户余额变动流水分页")
    @PreAuthorize("@ss.hasPermission('zc:customer-balance-log:query')")
    public CommonResult<PageResult<ZcCustomerBalanceLogRespVO>> getCustomerBalanceLogPage(@Valid ZcCustomerBalanceLogPageReqVO pageReqVO) {
        return success(customerBalanceLogService.getCustomerBalanceLogPage(pageReqVO));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出客户余额变动流水 Excel")
    @PreAuthorize("@ss.hasPermission('zc:customer-balance-log:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportCustomerBalanceLogExcel(@Valid ZcCustomerBalanceLogPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ZcCustomerBalanceLogRespVO> list = customerBalanceLogService.getCustomerBalanceLogPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "客户余额变动流水.xls", "数据", ZcCustomerBalanceLogRespVO.class, list);
    }

}