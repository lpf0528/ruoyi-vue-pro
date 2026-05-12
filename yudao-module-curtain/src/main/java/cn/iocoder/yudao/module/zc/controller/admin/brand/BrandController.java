package cn.iocoder.yudao.module.zc.controller.admin.brand;

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

import cn.iocoder.yudao.module.zc.controller.admin.brand.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.brand.BrandDO;
import cn.iocoder.yudao.module.zc.service.brand.BrandService;

@Tag(name = "管理后台 - 品牌")
@RestController
@RequestMapping("/zc/brand")
@Validated
public class BrandController {

    @Resource
    private BrandService brandService;

    @PostMapping("/create")
    @Operation(summary = "创建品牌")
    @PreAuthorize("@ss.hasPermission('zc:brand:create')")
    public CommonResult<Long> createBrand(@Valid @RequestBody BrandSaveReqVO createReqVO) {
        return success(brandService.createBrand(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新品牌")
    @PreAuthorize("@ss.hasPermission('zc:brand:update')")
    public CommonResult<Boolean> updateBrand(@Valid @RequestBody BrandSaveReqVO updateReqVO) {
        brandService.updateBrand(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除品牌")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('zc:brand:delete')")
    public CommonResult<Boolean> deleteBrand(@RequestParam("id") Long id) {
        brandService.deleteBrand(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除品牌")
                @PreAuthorize("@ss.hasPermission('zc:brand:delete')")
    public CommonResult<Boolean> deleteBrandList(@RequestParam("ids") List<Long> ids) {
        brandService.deleteBrandListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得品牌")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('zc:brand:query')")
    public CommonResult<BrandRespVO> getBrand(@RequestParam("id") Long id) {
        BrandDO brand = brandService.getBrand(id);
        return success(BeanUtils.toBean(brand, BrandRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得品牌分页")
    @PreAuthorize("@ss.hasPermission('zc:brand:query')")
    public CommonResult<PageResult<BrandRespVO>> getBrandPage(@Valid BrandPageReqVO pageReqVO) {
        PageResult<BrandDO> pageResult = brandService.getBrandPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, BrandRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出品牌 Excel")
    @PreAuthorize("@ss.hasPermission('zc:brand:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportBrandExcel(@Valid BrandPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<BrandDO> list = brandService.getBrandPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "品牌.xls", "数据", BrandRespVO.class,
                        BeanUtils.toBean(list, BrandRespVO.class));
    }

}