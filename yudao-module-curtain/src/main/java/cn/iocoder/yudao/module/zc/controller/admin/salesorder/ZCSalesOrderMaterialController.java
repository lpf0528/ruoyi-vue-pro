package cn.iocoder.yudao.module.zc.controller.admin.salesorder;

import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.constraints.*;
import jakarta.validation.*;
import jakarta.servlet.http.*;
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
import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZCSalesOrderMaterialDO;
import cn.iocoder.yudao.module.zc.service.salesorder.ZCSalesOrderMaterialService;

@Tag(name = "管理后台 - 成品订单-用料明细")
@RestController
@RequestMapping("/zc/sales-order-material")
@Validated
public class ZCSalesOrderMaterialController {

    @Resource
    private ZCSalesOrderMaterialService zCSalesOrderMaterialService;

    @GetMapping("/get")
    @Operation(summary = "获得成品订单-用料明细")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('zc:ZC-sales-order-material:query')")
    public CommonResult<ZCSalesOrderMaterialRespVO> getZCSalesOrderMaterial(@RequestParam("id") Long id) {
        ZCSalesOrderMaterialDO zCSalesOrderMaterial = zCSalesOrderMaterialService.getZCSalesOrderMaterial(id);
        return success(BeanUtils.toBean(zCSalesOrderMaterial, ZCSalesOrderMaterialRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得成品订单-用料明细分页")
    @PreAuthorize("@ss.hasPermission('zc:ZC-sales-order-material:query')")
    public CommonResult<PageResult<ZCSalesOrderMaterialRespVO>> getZCSalesOrderMaterialPage(@Valid ZCSalesOrderMaterialPageReqVO pageReqVO) {
        return success(zCSalesOrderMaterialService.getZCSalesOrderMaterialPage(pageReqVO));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出成品订单-用料明细 Excel")
    @PreAuthorize("@ss.hasPermission('zc:ZC-sales-order-material:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportZCSalesOrderMaterialExcel(@Valid ZCSalesOrderMaterialPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ZCSalesOrderMaterialRespVO> list = zCSalesOrderMaterialService.getZCSalesOrderMaterialPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "成品订单-用料明细.xls", "数据", ZCSalesOrderMaterialRespVO.class, list);
    }

}