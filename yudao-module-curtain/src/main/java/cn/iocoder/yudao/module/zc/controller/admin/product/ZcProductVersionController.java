package cn.iocoder.yudao.module.zc.controller.admin.product;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.zc.controller.admin.vo.product.ZcProductVersionSaveReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.product.ZcProductVersionPageReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.product.ZcProductVersionDO;
import cn.iocoder.yudao.module.zc.service.product.ZcProductVersionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 智仓产品版本")
@RestController
@RequestMapping("/zc/product-version")
@Validated
public class ZcProductVersionController {

    @Resource
    private ZcProductVersionService productVersionService;

    @PostMapping("/create")
    @Operation(summary = "创建产品版本")
    @PreAuthorize("@ss.hasPermission('zc:product-version:create')")
    public CommonResult<Long> create(@Valid @RequestBody ZcProductVersionSaveReqVO reqVO) {
        return success(productVersionService.create(reqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新产品版本")
    @PreAuthorize("@ss.hasPermission('zc:product-version:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody ZcProductVersionSaveReqVO reqVO) {
        productVersionService.update(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除产品版本")
    @PreAuthorize("@ss.hasPermission('zc:product-version:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        productVersionService.delete(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得产品版本")
    @PreAuthorize("@ss.hasPermission('zc:product-version:query')")
    public CommonResult<ZcProductVersionSaveReqVO> get(@RequestParam("id") Long id) {
        return success(BeanUtils.toBean(productVersionService.get(id), ZcProductVersionSaveReqVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "产品版本分页")
    @PreAuthorize("@ss.hasPermission('zc:product-version:query')")
    public CommonResult<PageResult<ZcProductVersionSaveReqVO>> page(@Valid ZcProductVersionPageReqVO pageReqVO) {
        PageResult<ZcProductVersionDO> pageResult = productVersionService.getPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ZcProductVersionSaveReqVO.class));
    }

}
