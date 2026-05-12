package cn.iocoder.yudao.module.zc.controller.admin.stock;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.zc.dal.dataobject.stock.ZcInventoryRecordDO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.stock.ZcInventoryRecordPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.stock.ZcInventoryRecordSaveReqVO;
import cn.iocoder.yudao.module.zc.service.stock.ZcInventoryRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 智仓盘点记录")
@RestController
@RequestMapping("/zc/inventory-record")
@Validated
public class ZcInventoryRecordController {

    @Resource
    private ZcInventoryRecordService inventoryRecordService;

    @PostMapping("/create")
    @Operation(summary = "创建盘点记录（同步更新批次剩余量）")
    @PreAuthorize("@ss.hasPermission('zc:inventory-record:create')")
    public CommonResult<Long> create(@Valid @RequestBody ZcInventoryRecordSaveReqVO reqVO) {
        return success(inventoryRecordService.create(reqVO));
    }

    @GetMapping("/get")
    @Operation(summary = "获得盘点记录")
    @PreAuthorize("@ss.hasPermission('zc:inventory-record:query')")
    public CommonResult<ZcInventoryRecordSaveReqVO> get(@RequestParam("id") Long id) {
        return success(BeanUtils.toBean(inventoryRecordService.get(id), ZcInventoryRecordSaveReqVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "盘点分页")
    @PreAuthorize("@ss.hasPermission('zc:inventory-record:query')")
    public CommonResult<PageResult<ZcInventoryRecordSaveReqVO>> page(@Valid ZcInventoryRecordPageReqVO pageReqVO) {
        PageResult<ZcInventoryRecordDO> pageResult = inventoryRecordService.getPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ZcInventoryRecordSaveReqVO.class));
    }

}
