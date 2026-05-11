package cn.iocoder.yudao.module.zc.controller.admin.product;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.zc.controller.admin.vo.product.ZcProductPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.product.ZcProductSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.product.ZcProductDO;
import cn.iocoder.yudao.module.zc.service.product.ZcProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 智仓货号")
@RestController
@RequestMapping("/zc/product")
@Validated
public class ZcProductController {

    @Resource
    private ZcProductService productService;

    @PostMapping("/create")
    @Operation(summary = "创建货号")
    @PreAuthorize("@ss.hasPermission('zc:product:create')")
    public CommonResult<Long> create(@Valid @RequestBody ZcProductSaveReqVO reqVO) {
        return success(productService.create(reqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新货号")
    @PreAuthorize("@ss.hasPermission('zc:product:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody ZcProductSaveReqVO reqVO) {
        productService.update(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除货号")
    @PreAuthorize("@ss.hasPermission('zc:product:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        productService.delete(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得货号")
    @PreAuthorize("@ss.hasPermission('zc:product:query')")
    public CommonResult<ZcProductSaveReqVO> get(@RequestParam("id") Long id) {
        return success(BeanUtils.toBean(productService.get(id), ZcProductSaveReqVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "货号分页")
    @PreAuthorize("@ss.hasPermission('zc:product:query')")
    public CommonResult<PageResult<ZcProductSaveReqVO>> page(@Valid ZcProductPageReqVO pageReqVO) {
        PageResult<ZcProductDO> pageResult = productService.getPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ZcProductSaveReqVO.class));
    }

}
