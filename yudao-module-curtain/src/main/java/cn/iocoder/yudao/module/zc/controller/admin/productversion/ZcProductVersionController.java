package cn.iocoder.yudao.module.zc.controller.admin.productversion;

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

import cn.iocoder.yudao.module.zc.controller.admin.productversion.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.productversion.ZcProductVersionDO;
import cn.iocoder.yudao.module.zc.service.productversion.ZcProductVersionService;

@Tag(name = "管理后台 - 产品版本")
@RestController
@RequestMapping("/zc/product-version")
@Validated
public class ZcProductVersionController {

    @Resource
    private ZcProductVersionService productVersionService;

    @PostMapping("/create")
    @Operation(summary = "创建产品版本")
    @PreAuthorize("@ss.hasPermission('zc:product-version:create')")
    public CommonResult<Long> createProductVersion(@Valid @RequestBody ZcProductVersionSaveReqVO createReqVO) {
        return success(productVersionService.createProductVersion(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新产品版本")
    @PreAuthorize("@ss.hasPermission('zc:product-version:update')")
    public CommonResult<Boolean> updateProductVersion(@Valid @RequestBody ZcProductVersionSaveReqVO updateReqVO) {
        productVersionService.updateProductVersion(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除产品版本")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('zc:product-version:delete')")
    public CommonResult<Boolean> deleteProductVersion(@RequestParam("id") Long id) {
        productVersionService.deleteProductVersion(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除产品版本")
                @PreAuthorize("@ss.hasPermission('zc:product-version:delete')")
    public CommonResult<Boolean> deleteProductVersionList(@RequestParam("ids") List<Long> ids) {
        productVersionService.deleteProductVersionListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得产品版本")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('zc:product-version:query')")
    public CommonResult<ZcProductVersionRespVO> getProductVersion(@RequestParam("id") Long id) {
        ZcProductVersionDO productVersion = productVersionService.getProductVersion(id);
        return success(BeanUtils.toBean(productVersion, ZcProductVersionRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得产品版本分页")
    @PreAuthorize("@ss.hasPermission('zc:product-version:query')")
    public CommonResult<PageResult<ZcProductVersionRespVO>> getProductVersionPage(@Valid ZcProductVersionPageReqVO pageReqVO) {
        return success(productVersionService.getProductVersionPage(pageReqVO));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出产品版本 Excel")
    @PreAuthorize("@ss.hasPermission('zc:product-version:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportProductVersionExcel(@Valid ZcProductVersionPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ZcProductVersionRespVO> list = productVersionService.getProductVersionPage(pageReqVO).getList();
        ExcelUtils.write(response, "产品版本.xls", "数据", ZcProductVersionRespVO.class, list);
    }

}