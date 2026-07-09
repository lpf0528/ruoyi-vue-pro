package cn.iocoder.yudao.module.zc.controller.admin.productcategory;

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
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.zc.controller.admin.productcategory.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.productcategory.ZcProductCategoryDO;
import cn.iocoder.yudao.module.zc.service.productcategory.ZcProductCategoryService;

@Tag(name = "管理后台 - 产品类别")
@RestController
@RequestMapping("/zc/product-category")
@Validated
public class ZcProductCategoryController {

    @Resource
    private ZcProductCategoryService productCategoryService;

    @PostMapping("/create")
    @Operation(summary = "创建产品类别")
    @PreAuthorize("@ss.hasPermission('zc:product-category:create')")
    public CommonResult<Long> createProductCategory(@Valid @RequestBody ZcProductCategorySaveReqVO createReqVO) {
        return success(productCategoryService.createProductCategory(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新产品类别")
    @PreAuthorize("@ss.hasPermission('zc:product-category:update')")
    public CommonResult<Boolean> updateProductCategory(@Valid @RequestBody ZcProductCategorySaveReqVO updateReqVO) {
        productCategoryService.updateProductCategory(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除产品类别")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('zc:product-category:delete')")
    public CommonResult<Boolean> deleteProductCategory(@RequestParam("id") Long id) {
        productCategoryService.deleteProductCategory(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除产品类别")
                @PreAuthorize("@ss.hasPermission('zc:product-category:delete')")
    public CommonResult<Boolean> deleteProductCategoryList(@RequestParam("ids") List<Long> ids) {
        productCategoryService.deleteProductCategoryListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得产品类别")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('zc:product-category:query')")
    public CommonResult<ZcProductCategoryRespVO> getProductCategory(@RequestParam("id") Long id) {
        ZcProductCategoryDO productCategory = productCategoryService.getProductCategory(id);
        return success(BeanUtils.toBean(productCategory, ZcProductCategoryRespVO.class));
    }

    @GetMapping("/simple-list")
    @Operation(summary = "获得产品类别精简列表", description = "主要用于前端的下拉选项")
    public CommonResult<List<ZcProductCategorySimpleRespVO>> getProductCategorySimpleList() {
        List<ZcProductCategoryDO> list = productCategoryService.getProductCategoryList(new ZcProductCategoryListReqVO());
        return success(convertList(list, item -> new ZcProductCategorySimpleRespVO()
                .setId(item.getId()).setValue(item.getValue())));
    }

    @GetMapping("/page")
    @Operation(summary = "获得产品类别分页")
    @PreAuthorize("@ss.hasPermission('zc:product-category:query')")
    public CommonResult<PageResult<ZcProductCategoryRespVO>> getProductCategoryPage(@Valid ZcProductCategoryPageReqVO pageReqVO) {
        PageResult<ZcProductCategoryDO> pageResult = productCategoryService.getProductCategoryPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ZcProductCategoryRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出产品类别 Excel")
    @PreAuthorize("@ss.hasPermission('zc:product-category:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportProductCategoryExcel(@Valid ZcProductCategoryPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ZcProductCategoryDO> list = productCategoryService.getProductCategoryPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "产品类别.xls", "数据", ZcProductCategoryRespVO.class,
                        BeanUtils.toBean(list, ZcProductCategoryRespVO.class));
    }

}