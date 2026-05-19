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
import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

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

    @PostMapping("/create-batch")
    @Operation(summary = "批量创建客户产品销售授权价")
    @PreAuthorize("@ss.hasPermission('zc:customer-product-price:create')")
    public CommonResult<Boolean> createCustomerProductPriceList(@Valid @RequestBody List<ZcCustomerProductPriceSaveReqVO> createReqVOs) {
        customerProductPriceService.createCustomerProductPriceList(createReqVOs);
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

}
