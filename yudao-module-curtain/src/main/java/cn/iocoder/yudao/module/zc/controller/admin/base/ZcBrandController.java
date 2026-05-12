package cn.iocoder.yudao.module.zc.controller.admin.base;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.zc.controller.admin.vo.base.ZcBrandPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.base.ZcBrandSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.base.ZcBrandDO;
import cn.iocoder.yudao.module.zc.service.base.ZcBrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 智仓品牌")
@RestController
@RequestMapping("/zc/brand")
@Validated
public class ZcBrandController {

    @Resource
    private ZcBrandService brandService;

    @PostMapping("/create")
    @Operation(summary = "创建品牌")
    @PreAuthorize("@ss.hasPermission('zc:brand:create')")
    public CommonResult<Long> create(@Valid @RequestBody ZcBrandSaveReqVO reqVO) {
        return success(brandService.create(reqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新品牌")
    @PreAuthorize("@ss.hasPermission('zc:brand:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody ZcBrandSaveReqVO reqVO) {
        brandService.update(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除品牌")
    @PreAuthorize("@ss.hasPermission('zc:brand:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        brandService.delete(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得品牌")
    @PreAuthorize("@ss.hasPermission('zc:brand:query')")
    public CommonResult<ZcBrandSaveReqVO> get(@RequestParam("id") Long id) {
        return success(BeanUtils.toBean(brandService.get(id), ZcBrandSaveReqVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "品牌分页")
    @PreAuthorize("@ss.hasPermission('zc:brand:query')")
    public CommonResult<PageResult<ZcBrandSaveReqVO>> page(@Valid ZcBrandPageReqVO pageReqVO) {
        PageResult<ZcBrandDO> pageResult = brandService.getPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ZcBrandSaveReqVO.class));
    }

}
