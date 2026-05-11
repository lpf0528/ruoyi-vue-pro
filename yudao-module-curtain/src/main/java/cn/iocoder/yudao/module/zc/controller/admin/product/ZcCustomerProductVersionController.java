package cn.iocoder.yudao.module.zc.controller.admin.product;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.zc.controller.admin.vo.product.ZcCustomerProductVersionPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.product.ZcCustomerProductVersionSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.product.ZcCustomerProductVersionDO;
import cn.iocoder.yudao.module.zc.service.product.ZcCustomerProductVersionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 智仓客户版本授权")
@RestController
@RequestMapping("/zc/customer-product-version")
@Validated
public class ZcCustomerProductVersionController {

    @Resource
    private ZcCustomerProductVersionService customerProductVersionService;

    @PostMapping("/create")
    @Operation(summary = "创建客户版本授权")
    @PreAuthorize("@ss.hasPermission('zc:customer-product-version:create')")
    public CommonResult<Long> create(@Valid @RequestBody ZcCustomerProductVersionSaveReqVO reqVO) {
        return success(customerProductVersionService.create(reqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新客户版本授权")
    @PreAuthorize("@ss.hasPermission('zc:customer-product-version:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody ZcCustomerProductVersionSaveReqVO reqVO) {
        customerProductVersionService.update(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除客户版本授权")
    @PreAuthorize("@ss.hasPermission('zc:customer-product-version:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        customerProductVersionService.delete(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得客户版本授权")
    @PreAuthorize("@ss.hasPermission('zc:customer-product-version:query')")
    public CommonResult<ZcCustomerProductVersionSaveReqVO> get(@RequestParam("id") Long id) {
        return success(BeanUtils.toBean(customerProductVersionService.get(id), ZcCustomerProductVersionSaveReqVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "客户版本授权分页")
    @PreAuthorize("@ss.hasPermission('zc:customer-product-version:query')")
    public CommonResult<PageResult<ZcCustomerProductVersionSaveReqVO>> page(
            @Valid ZcCustomerProductVersionPageReqVO pageReqVO) {
        PageResult<ZcCustomerProductVersionDO> pageResult = customerProductVersionService.getPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ZcCustomerProductVersionSaveReqVO.class));
    }

}
