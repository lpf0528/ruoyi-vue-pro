package cn.iocoder.yudao.module.zc.controller.admin.stock;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.zc.dal.dataobject.stock.ZcProductBatchDO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.stock.ZcProductBatchDeductReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.stock.ZcProductBatchPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.stock.ZcProductBatchSaveReqVO;
import cn.iocoder.yudao.module.zc.service.stock.ZcProductBatchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 智仓产品批次")
@RestController
@RequestMapping("/zc/product-batch")
@Validated
public class ZcProductBatchController {

    @Resource
    private ZcProductBatchService productBatchService;

    @PostMapping("/create")
    @Operation(summary = "批次入库")
    @PreAuthorize("@ss.hasPermission('zc:product-batch:create')")
    public CommonResult<Long> create(@Valid @RequestBody ZcProductBatchSaveReqVO reqVO) {
        return success(productBatchService.createInbound(reqVO));
    }

    @PostMapping("/deduct")
    @Operation(summary = "扣减批次剩余数量")
    @PreAuthorize("@ss.hasPermission('zc:product-batch:deduct')")
    public CommonResult<Boolean> deduct(@Valid @RequestBody ZcProductBatchDeductReqVO reqVO) {
        productBatchService.deductQuantity(reqVO.getBatchId(), reqVO.getQuantity());
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得批次")
    @PreAuthorize("@ss.hasPermission('zc:product-batch:query')")
    public CommonResult<ZcProductBatchSaveReqVO> get(@RequestParam("id") Long id) {
        return success(BeanUtils.toBean(productBatchService.get(id), ZcProductBatchSaveReqVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "批次分页")
    @PreAuthorize("@ss.hasPermission('zc:product-batch:query')")
    public CommonResult<PageResult<ZcProductBatchSaveReqVO>> page(@Valid ZcProductBatchPageReqVO pageReqVO) {
        PageResult<ZcProductBatchDO> pageResult = productBatchService.getPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ZcProductBatchSaveReqVO.class));
    }

}
