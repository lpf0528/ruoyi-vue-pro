package cn.iocoder.yudao.module.zc.controller.admin.base;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.zc.controller.admin.vo.base.ZcWarehousePageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.base.ZcWarehouseSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.base.ZcWarehouseDO;
import cn.iocoder.yudao.module.zc.service.base.ZcWarehouseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 智仓仓库")
@RestController
@RequestMapping("/zc/warehouse")
@Validated
public class ZcWarehouseController {

    @Resource
    private ZcWarehouseService warehouseService;

    @PostMapping("/create")
    @Operation(summary = "创建仓库")
    @PreAuthorize("@ss.hasPermission('zc:warehouse:create')")
    public CommonResult<Long> createWarehouse(@Valid @RequestBody ZcWarehouseSaveReqVO createReqVO) {
        return success(warehouseService.createWarehouse(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新仓库")
    @PreAuthorize("@ss.hasPermission('zc:warehouse:update')")
    public CommonResult<Boolean> updateWarehouse(@Valid @RequestBody ZcWarehouseSaveReqVO updateReqVO) {
        warehouseService.updateWarehouse(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除仓库")
    @PreAuthorize("@ss.hasPermission('zc:warehouse:delete')")
    public CommonResult<Boolean> deleteWarehouse(@RequestParam("id") Long id) {
        warehouseService.deleteWarehouse(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得仓库")
    @PreAuthorize("@ss.hasPermission('zc:warehouse:query')")
    public CommonResult<ZcWarehouseSaveReqVO> getWarehouse(@RequestParam("id") Long id) {
        return success(BeanUtils.toBean(warehouseService.getWarehouse(id), ZcWarehouseSaveReqVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "仓库分页")
    @PreAuthorize("@ss.hasPermission('zc:warehouse:query')")
    public CommonResult<PageResult<ZcWarehouseSaveReqVO>> getWarehousePage(@Valid ZcWarehousePageReqVO pageReqVO) {
        PageResult<ZcWarehouseDO> pageResult = warehouseService.getWarehousePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ZcWarehouseSaveReqVO.class));
    }

}
