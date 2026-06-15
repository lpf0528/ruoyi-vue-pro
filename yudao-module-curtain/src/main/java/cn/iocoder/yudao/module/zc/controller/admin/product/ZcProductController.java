package cn.iocoder.yudao.module.zc.controller.admin.product;

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

import cn.iocoder.yudao.module.zc.controller.admin.product.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.product.ZcProductDO;
import cn.iocoder.yudao.module.zc.service.product.ZcProductService;

@Tag(name = "管理后台 - 产品")
@RestController
@RequestMapping("/zc/product")
@Validated
public class ZcProductController {

    @Resource
    private ZcProductService productService;

    @PostMapping("/create")
    @Operation(summary = "创建产品")
    @PreAuthorize("@ss.hasPermission('zc:product:create')")
    public CommonResult<Long> createProduct(@Valid @RequestBody ZcProductSaveReqVO createReqVO) {
        return success(productService.createProduct(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新产品")
    @PreAuthorize("@ss.hasPermission('zc:product:update')")
    public CommonResult<Boolean> updateProduct(@Valid @RequestBody ZcProductSaveReqVO updateReqVO) {
        productService.updateProduct(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除产品")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('zc:product:delete')")
    public CommonResult<Boolean> deleteProduct(@RequestParam("id") Long id) {
        productService.deleteProduct(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除产品")
                @PreAuthorize("@ss.hasPermission('zc:product:delete')")
    public CommonResult<Boolean> deleteProductList(@RequestParam("ids") List<Long> ids) {
        productService.deleteProductListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得产品")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('zc:product:query')")
    public CommonResult<ZcProductRespVO> getProduct(@RequestParam("id") Long id) {
        ZcProductDO product = productService.getProduct(id);
        return success(BeanUtils.toBean(product, ZcProductRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得产品分页")
    @PreAuthorize("@ss.hasPermission('zc:product:query')")
    public CommonResult<PageResult<ZcProductRespVO>> getProductPage(@Valid ZcProductPageReqVO pageReqVO) {
        return success(productService.getProductPage(pageReqVO));
    }

    @GetMapping("/simple-list")
    @Operation(summary = "获得产品精简列表", description = "主要用于前端的下拉选项")
    public CommonResult<List<ZcProductSimpleRespVO>> getProductSimpleList(ZcProductListReqVO reqVO) {
        List<ZcProductSimpleRespVO> list = productService.getProductSimpleList(reqVO);
        return success(list);
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出产品 Excel")
    @PreAuthorize("@ss.hasPermission('zc:product:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportProductExcel(@Valid ZcProductPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ZcProductRespVO> list = productService.getProductPage(pageReqVO).getList();
        ExcelUtils.write(response, "产品.xls", "数据", ZcProductRespVO.class, list);
    }

}