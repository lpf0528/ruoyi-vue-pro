package cn.iocoder.yudao.module.zc.controller.admin.product;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.zc.controller.admin.vo.product.ZcProductUnitSaveReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.product.ZcProductUnitPageReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.product.ZcProductUnitDO;
import cn.iocoder.yudao.module.zc.service.product.ZcProductUnitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 智仓单位")
@RestController
@RequestMapping("/zc/product-unit")
@Validated
public class ZcProductUnitController {

    @Resource
    private ZcProductUnitService productUnitService;

    @PostMapping("/create")
    @Operation(summary = "创建单位")
    @PreAuthorize("@ss.hasPermission('zc:product-unit:create')")
    public CommonResult<Long> create(@Valid @RequestBody ZcProductUnitSaveReqVO reqVO) {
        return success(productUnitService.create(reqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新单位")
    @PreAuthorize("@ss.hasPermission('zc:product-unit:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody ZcProductUnitSaveReqVO reqVO) {
        productUnitService.update(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除单位")
    @PreAuthorize("@ss.hasPermission('zc:product-unit:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        productUnitService.delete(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得单位")
    @PreAuthorize("@ss.hasPermission('zc:product-unit:query')")
    public CommonResult<ZcProductUnitSaveReqVO> get(@RequestParam("id") Long id) {
        return success(BeanUtils.toBean(productUnitService.get(id), ZcProductUnitSaveReqVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "单位分页")
    @PreAuthorize("@ss.hasPermission('zc:product-unit:query')")
    public CommonResult<PageResult<ZcProductUnitSaveReqVO>> page(@Valid ZcProductUnitPageReqVO pageReqVO) {
        PageResult<ZcProductUnitDO> pageResult = productUnitService.getPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ZcProductUnitSaveReqVO.class));
    }

}
