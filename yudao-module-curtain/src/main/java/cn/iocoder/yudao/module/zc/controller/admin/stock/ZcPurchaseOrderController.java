package cn.iocoder.yudao.module.zc.controller.admin.stock;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.zc.dal.dataobject.stock.ZcPurchaseOrderDO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.stock.ZcPurchaseOrderPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.stock.ZcPurchaseOrderSaveReqVO;
import cn.iocoder.yudao.module.zc.service.stock.ZcPurchaseOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 智仓采购单")
@RestController
@RequestMapping("/zc/purchase-order")
@Validated
public class ZcPurchaseOrderController {

    @Resource
    private ZcPurchaseOrderService purchaseOrderService;

    @PostMapping("/create")
    @Operation(summary = "创建采购单")
    @PreAuthorize("@ss.hasPermission('zc:purchase-order:create')")
    public CommonResult<Long> create(@Valid @RequestBody ZcPurchaseOrderSaveReqVO reqVO) {
        return success(purchaseOrderService.create(reqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新采购单（未审核）")
    @PreAuthorize("@ss.hasPermission('zc:purchase-order:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody ZcPurchaseOrderSaveReqVO reqVO) {
        purchaseOrderService.update(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除采购单（未审核）")
    @PreAuthorize("@ss.hasPermission('zc:purchase-order:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        purchaseOrderService.delete(id);
        return success(true);
    }

    @PutMapping("/audit")
    @Operation(summary = "审核采购单")
    @PreAuthorize("@ss.hasPermission('zc:purchase-order:audit')")
    public CommonResult<Boolean> audit(@RequestParam("id") Long id) {
        purchaseOrderService.audit(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得采购单")
    @PreAuthorize("@ss.hasPermission('zc:purchase-order:query')")
    public CommonResult<ZcPurchaseOrderSaveReqVO> get(@RequestParam("id") Long id) {
        return success(BeanUtils.toBean(purchaseOrderService.get(id), ZcPurchaseOrderSaveReqVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "采购单分页")
    @PreAuthorize("@ss.hasPermission('zc:purchase-order:query')")
    public CommonResult<PageResult<ZcPurchaseOrderSaveReqVO>> page(@Valid ZcPurchaseOrderPageReqVO pageReqVO) {
        PageResult<ZcPurchaseOrderDO> pageResult = purchaseOrderService.getPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ZcPurchaseOrderSaveReqVO.class));
    }

}
