package cn.iocoder.yudao.module.zc.controller.admin.product;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.zc.controller.admin.vo.product.ZcProductCategoryPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.product.ZcProductCategorySaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.product.ZcProductCategoryDO;
import cn.iocoder.yudao.module.zc.service.product.ZcProductCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 智仓货号类别")
@RestController
@RequestMapping("/zc/product-category")
@Validated
public class ZcProductCategoryController {

    @Resource
    private ZcProductCategoryService productCategoryService;

    @PostMapping("/create")
    @Operation(summary = "创建货号类别")
    @PreAuthorize("@ss.hasPermission('zc:product-category:create')")
    public CommonResult<Long> create(@Valid @RequestBody ZcProductCategorySaveReqVO reqVO) {
        return success(productCategoryService.create(reqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新货号类别")
    @PreAuthorize("@ss.hasPermission('zc:product-category:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody ZcProductCategorySaveReqVO reqVO) {
        productCategoryService.update(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除货号类别")
    @PreAuthorize("@ss.hasPermission('zc:product-category:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        productCategoryService.delete(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得货号类别")
    @PreAuthorize("@ss.hasPermission('zc:product-category:query')")
    public CommonResult<ZcProductCategorySaveReqVO> get(@RequestParam("id") Long id) {
        return success(BeanUtils.toBean(productCategoryService.get(id), ZcProductCategorySaveReqVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "货号类别分页")
    @PreAuthorize("@ss.hasPermission('zc:product-category:query')")
    public CommonResult<PageResult<ZcProductCategorySaveReqVO>> page(@Valid ZcProductCategoryPageReqVO pageReqVO) {
        PageResult<ZcProductCategoryDO> pageResult = productCategoryService.getPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ZcProductCategorySaveReqVO.class));
    }

}
