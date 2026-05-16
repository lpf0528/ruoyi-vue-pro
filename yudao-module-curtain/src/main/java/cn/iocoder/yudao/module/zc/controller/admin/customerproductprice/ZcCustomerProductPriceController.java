package cn.iocoder.yudao.module.zc.controller.admin.customerproductprice;

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

import cn.iocoder.yudao.module.zc.controller.admin.customerproductprice.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.customerproductprice.ZcCustomerProductPriceDO;
import cn.iocoder.yudao.module.zc.service.customerproductprice.ZcCustomerProductPriceService;

@Tag(name = "管理后台 - 客户产品销售授权价")
@RestController
@RequestMapping("/zc/customer-product-price")
@Validated
public class ZcCustomerProductPriceController {

    @Resource
    private ZcCustomerProductPriceService customerProductPriceService;

    @PostMapping("/create")
    @Operation(summary = "创建客户产品销售授权价")
    @PreAuthorize("@ss.hasPermission('zc:customer-product-price:create')")
    public CommonResult<Long> createCustomerProductPrice(@Valid @RequestBody ZcCustomerProductPriceSaveReqVO createReqVO) {
        return success(customerProductPriceService.createCustomerProductPrice(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新客户产品销售授权价")
    @PreAuthorize("@ss.hasPermission('zc:customer-product-price:update')")
    public CommonResult<Boolean> updateCustomerProductPrice(@Valid @RequestBody ZcCustomerProductPriceSaveReqVO updateReqVO) {
        customerProductPriceService.updateCustomerProductPrice(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除客户产品销售授权价")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('zc:customer-product-price:delete')")
    public CommonResult<Boolean> deleteCustomerProductPrice(@RequestParam("id") Long id) {
        customerProductPriceService.deleteCustomerProductPrice(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除客户产品销售授权价")
                @PreAuthorize("@ss.hasPermission('zc:customer-product-price:delete')")
    public CommonResult<Boolean> deleteCustomerProductPriceList(@RequestParam("ids") List<Long> ids) {
        customerProductPriceService.deleteCustomerProductPriceListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得客户产品销售授权价")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('zc:customer-product-price:query')")
    public CommonResult<ZcCustomerProductPriceRespVO> getCustomerProductPrice(@RequestParam("id") Long id) {
        ZcCustomerProductPriceDO customerProductPrice = customerProductPriceService.getCustomerProductPrice(id);
        return success(BeanUtils.toBean(customerProductPrice, ZcCustomerProductPriceRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得客户产品销售授权价分页")
    @PreAuthorize("@ss.hasPermission('zc:customer-product-price:query')")
    public CommonResult<PageResult<ZcCustomerProductPriceRespVO>> getCustomerProductPricePage(@Valid ZcCustomerProductPricePageReqVO pageReqVO) {
        return success(customerProductPriceService.getCustomerProductPricePage(pageReqVO));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出客户产品销售授权价 Excel")
    @PreAuthorize("@ss.hasPermission('zc:customer-product-price:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportCustomerProductPriceExcel(@Valid ZcCustomerProductPricePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ZcCustomerProductPriceRespVO> list = customerProductPriceService.getCustomerProductPricePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "客户产品销售授权价.xls", "数据", ZcCustomerProductPriceRespVO.class, list);
    }

}