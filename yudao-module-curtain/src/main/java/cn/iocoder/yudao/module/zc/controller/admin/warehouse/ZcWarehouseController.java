package cn.iocoder.yudao.module.zc.controller.admin.warehouse;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.constraints.*;
import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.zc.controller.admin.warehouse.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.warehouse.ZcWarehouseDO;
import cn.iocoder.yudao.module.zc.service.warehouse.ZcWarehouseService;

@Tag(name = "管理后台 - 仓库")
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
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('zc:warehouse:delete')")
    public CommonResult<Boolean> deleteWarehouse(@RequestParam("id") Long id) {
        warehouseService.deleteWarehouse(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除仓库")
                @PreAuthorize("@ss.hasPermission('zc:warehouse:delete')")
    public CommonResult<Boolean> deleteWarehouseList(@RequestParam("ids") List<Long> ids) {
        warehouseService.deleteWarehouseListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得仓库")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('zc:warehouse:query')")
    public CommonResult<ZcWarehouseRespVO> getWarehouse(@RequestParam("id") Long id) {
        // 使用 JOIN 查询，返回含负责人昵称的 VO
        return success(warehouseService.getWarehouseVO(id));
    }

    @GetMapping("/page")
    @Operation(summary = "获得仓库分页")
    @PreAuthorize("@ss.hasPermission('zc:warehouse:query')")
    public CommonResult<PageResult<ZcWarehouseRespVO>> getWarehousePage(@Valid ZcWarehousePageReqVO pageReqVO) {
        // 使用 JOIN 查询，返回含负责人昵称的分页 VO
        return success(warehouseService.getWarehousePageVO(pageReqVO));
    }

    @GetMapping("/simple-list")
    @Operation(summary = "获得仓库精简列表", description = "主要用于前端的下拉选项")
    public CommonResult<List<ZcWarehouseSimpleRespVO>> getWarehouseSimpleList() {
        List<ZcWarehouseDO> list = warehouseService.getWarehouseList(new ZcWarehouseListReqVO());
        return success(convertList(list, item -> new ZcWarehouseSimpleRespVO()
                .setId(item.getId())
                .setName(item.getName())
                .setDefaultStatus(item.getDefaultStatus())));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出仓库 Excel")
    @PreAuthorize("@ss.hasPermission('zc:warehouse:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportWarehouseExcel(@Valid ZcWarehousePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ZcWarehouseDO> list = warehouseService.getWarehousePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "仓库.xls", "数据", ZcWarehouseRespVO.class,
                        BeanUtils.toBean(list, ZcWarehouseRespVO.class));
    }

}