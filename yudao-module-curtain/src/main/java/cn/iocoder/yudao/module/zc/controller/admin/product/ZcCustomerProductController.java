package cn.iocoder.yudao.module.zc.controller.admin.product;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.zc.controller.admin.vo.product.ZcCustomerProductPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.product.ZcCustomerProductSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.product.ZcCustomerProductDO;
import cn.iocoder.yudao.module.zc.service.product.ZcCustomerProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 智仓客户货号授权")
@RestController
@RequestMapping("/zc/customer-product")
@Validated
public class ZcCustomerProductController {

    @Resource
    private ZcCustomerProductService customerProductService;

    @PostMapping("/create")
    @Operation(summary = "创建客户货号授权")
    @PreAuthorize("@ss.hasPermission('zc:customer-product:create')")
    public CommonResult<Long> create(@Valid @RequestBody ZcCustomerProductSaveReqVO reqVO) {
        return success(customerProductService.create(reqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新客户货号授权")
    @PreAuthorize("@ss.hasPermission('zc:customer-product:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody ZcCustomerProductSaveReqVO reqVO) {
        customerProductService.update(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除客户货号授权")
    @PreAuthorize("@ss.hasPermission('zc:customer-product:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        customerProductService.delete(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得客户货号授权")
    @PreAuthorize("@ss.hasPermission('zc:customer-product:query')")
    public CommonResult<ZcCustomerProductSaveReqVO> get(@RequestParam("id") Long id) {
        return success(BeanUtils.toBean(customerProductService.get(id), ZcCustomerProductSaveReqVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "客户货号授权分页")
    @PreAuthorize("@ss.hasPermission('zc:customer-product:query')")
    public CommonResult<PageResult<ZcCustomerProductSaveReqVO>> page(@Valid ZcCustomerProductPageReqVO pageReqVO) {
        PageResult<ZcCustomerProductDO> pageResult = customerProductService.getPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ZcCustomerProductSaveReqVO.class));
    }

}
