package cn.iocoder.yudao.module.zc.controller.admin.base;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.zc.controller.admin.vo.base.ZcSupplierPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.base.ZcSupplierSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.base.ZcSupplierDO;
import cn.iocoder.yudao.module.zc.service.base.ZcSupplierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 智仓供应商")
@RestController
@RequestMapping("/zc/supplier")
@Validated
public class ZcSupplierController {

    @Resource
    private ZcSupplierService supplierService;

    @PostMapping("/create")
    @Operation(summary = "创建供应商")
    @PreAuthorize("@ss.hasPermission('zc:supplier:create')")
    public CommonResult<Long> create(@Valid @RequestBody ZcSupplierSaveReqVO reqVO) {
        return success(supplierService.create(reqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新供应商")
    @PreAuthorize("@ss.hasPermission('zc:supplier:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody ZcSupplierSaveReqVO reqVO) {
        supplierService.update(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除供应商")
    @PreAuthorize("@ss.hasPermission('zc:supplier:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        supplierService.delete(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得供应商")
    @PreAuthorize("@ss.hasPermission('zc:supplier:query')")
    public CommonResult<ZcSupplierSaveReqVO> get(@RequestParam("id") Long id) {
        return success(BeanUtils.toBean(supplierService.get(id), ZcSupplierSaveReqVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "供应商分页")
    @PreAuthorize("@ss.hasPermission('zc:supplier:query')")
    public CommonResult<PageResult<ZcSupplierSaveReqVO>> page(@Valid ZcSupplierPageReqVO pageReqVO) {
        PageResult<ZcSupplierDO> pageResult = supplierService.getPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ZcSupplierSaveReqVO.class));
    }

}
