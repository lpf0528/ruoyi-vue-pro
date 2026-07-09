package cn.iocoder.yudao.module.zc.controller.admin.supplier;

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
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.zc.controller.admin.supplier.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.supplier.ZcSupplierDO;
import cn.iocoder.yudao.module.zc.service.supplier.ZcSupplierService;

@Tag(name = "管理后台 - 供应商")
@RestController
@RequestMapping("/zc/supplier")
@Validated
public class ZcSupplierController {

    @Resource
    private ZcSupplierService supplierService;

    @PostMapping("/create")
    @Operation(summary = "创建供应商")
    @PreAuthorize("@ss.hasPermission('zc:supplier:create')")
    public CommonResult<Long> createSupplier(@Valid @RequestBody ZcSupplierSaveReqVO createReqVO) {
        return success(supplierService.createSupplier(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新供应商")
    @PreAuthorize("@ss.hasPermission('zc:supplier:update')")
    public CommonResult<Boolean> updateSupplier(@Valid @RequestBody ZcSupplierSaveReqVO updateReqVO) {
        supplierService.updateSupplier(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除供应商")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('zc:supplier:delete')")
    public CommonResult<Boolean> deleteSupplier(@RequestParam("id") Long id) {
        supplierService.deleteSupplier(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除供应商")
                @PreAuthorize("@ss.hasPermission('zc:supplier:delete')")
    public CommonResult<Boolean> deleteSupplierList(@RequestParam("ids") List<Long> ids) {
        supplierService.deleteSupplierListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得供应商")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('zc:supplier:query')")
    public CommonResult<ZcSupplierRespVO> getSupplier(@RequestParam("id") Long id) {
        ZcSupplierDO supplier = supplierService.getSupplier(id);
        return success(BeanUtils.toBean(supplier, ZcSupplierRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得供应商分页")
    @PreAuthorize("@ss.hasPermission('zc:supplier:query')")
    public CommonResult<PageResult<ZcSupplierRespVO>> getSupplierPage(@Valid ZcSupplierPageReqVO pageReqVO) {
        PageResult<ZcSupplierDO> pageResult = supplierService.getSupplierPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ZcSupplierRespVO.class));
    }

    @GetMapping("/simple-list")
    @Operation(summary = "获得供应商精简列表", description = "主要用于前端的下拉选项")
    public CommonResult<List<ZcSupplierSimpleRespVO>> getSupplierSimpleList() {
        List<ZcSupplierDO> list = supplierService.getSupplierList(new ZcSupplierListReqVO());
        return success(convertList(list, item -> new ZcSupplierSimpleRespVO()
                .setId(item.getId())
                .setShortName(item.getShortName())));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出供应商 Excel")
    @PreAuthorize("@ss.hasPermission('zc:supplier:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSupplierExcel(@Valid ZcSupplierPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ZcSupplierDO> list = supplierService.getSupplierPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "供应商.xls", "数据", ZcSupplierRespVO.class,
                        BeanUtils.toBean(list, ZcSupplierRespVO.class));
    }

}