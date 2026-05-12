package cn.iocoder.yudao.module.zc.controller.admin.product;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.zc.controller.admin.vo.product.ZcProductSpecPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.product.ZcProductSpecSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.product.ZcProductSpecDO;
import cn.iocoder.yudao.module.zc.service.product.ZcProductSpecService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 智仓规格")
@RestController
@RequestMapping("/zc/product-spec")
@Validated
public class ZcProductSpecController {

    @Resource
    private ZcProductSpecService productSpecService;

    @PostMapping("/create")
    @Operation(summary = "创建规格")
    @PreAuthorize("@ss.hasPermission('zc:product-spec:create')")
    public CommonResult<Long> create(@Valid @RequestBody ZcProductSpecSaveReqVO reqVO) {
        return success(productSpecService.create(reqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新规格")
    @PreAuthorize("@ss.hasPermission('zc:product-spec:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody ZcProductSpecSaveReqVO reqVO) {
        productSpecService.update(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除规格")
    @PreAuthorize("@ss.hasPermission('zc:product-spec:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        productSpecService.delete(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得规格")
    @PreAuthorize("@ss.hasPermission('zc:product-spec:query')")
    public CommonResult<ZcProductSpecSaveReqVO> get(@RequestParam("id") Long id) {
        return success(BeanUtils.toBean(productSpecService.get(id), ZcProductSpecSaveReqVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "规格分页")
    @PreAuthorize("@ss.hasPermission('zc:product-spec:query')")
    public CommonResult<PageResult<ZcProductSpecSaveReqVO>> page(@Valid ZcProductSpecPageReqVO pageReqVO) {
        PageResult<ZcProductSpecDO> pageResult = productSpecService.getPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ZcProductSpecSaveReqVO.class));
    }

}
