package cn.iocoder.yudao.module.zc.controller.admin.salesorder;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.ZcCancelCutProductReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.ZcCancelShipProductReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.ZcCutProductReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.ZcShipProductReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.ZcSalesOrderProductCreateReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.ZcSalesOrderProductDetailRespVO;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.ZcSalesOrderProductUpdateReqVO;
import cn.iocoder.yudao.module.zc.service.salesorder.ZcSalesOrderProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * 管理后台 - 产品类销售订单 Controller
 *
 * <p>处理面料单等直接购买产品批次的订单类型，
 * 与窗帘成品订单（{@code /zc/sales-order}）共用主表 zc_sales_order，
 * 产品明细独立存储于 zc_sales_order_product。</p>
 *
 * @author 01Coder
 */
@Tag(name = "管理后台 - 产品类销售订单")
@RestController
@RequestMapping("/zc/sales-order-product")
@Validated
public class ZcSalesOrderProductController {

    @Resource
    private ZcSalesOrderProductService salesOrderProductService;

    /**
     * 创建产品类销售订单（整单，含产品批次行）
     */
    @PostMapping("/create")
    @Operation(summary = "创建产品类销售订单（整单，含产品批次行）")
    @PreAuthorize("@ss.hasPermission('zc:sales-order:create')")
    public CommonResult<Long> createSalesOrderProduct(
            @Valid @RequestBody ZcSalesOrderProductCreateReqVO createReqVO) {
        return success(salesOrderProductService.createSalesOrderProduct(createReqVO));
    }

    /**
     * 删除产品类销售订单（级联删除产品行）
     */
    @DeleteMapping("/delete")
    @Operation(summary = "删除产品类销售订单")
    @Parameter(name = "id", description = "订单 ID", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('zc:sales-order:delete')")
    public CommonResult<Boolean> deleteSalesOrderProduct(@RequestParam("id") Long id) {
        salesOrderProductService.deleteSalesOrderProduct(id);
        return success(true);
    }

    /**
     * 更新产品类销售订单（整单，含产品批次行）
     */
    @PutMapping("/update")
    @Operation(summary = "更新产品类销售订单（整单，含产品批次行）")
    @PreAuthorize("@ss.hasPermission('zc:sales-order:update')")
    public CommonResult<Boolean> updateSalesOrderProduct(
            @Valid @RequestBody ZcSalesOrderProductUpdateReqVO updateReqVO) {
        salesOrderProductService.updateSalesOrderProduct(updateReqVO);
        return success(true);
    }

    /**
     * 查询面料单详情（含产品批次行列表）
     */
    @GetMapping("/detail")
    @Operation(summary = "查询面料单详情（含产品批次行列表）")
    @Parameter(name = "id", description = "订单 ID", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('zc:sales-order:query')")
    public CommonResult<ZcSalesOrderProductDetailRespVO> getSalesOrderProductDetail(
            @RequestParam("id") Long id) {
        return success(salesOrderProductService.getSalesOrderProductDetail(id));
    }

    /**
     * 面料单产品行裁剪（扣减批次库存、记录裁剪数量、更新配料状态）
     */
    @PutMapping("/cut")
    @Operation(summary = "面料单产品行裁剪（扣减批次库存、记录裁剪数量、更新配料状态）")
    @PreAuthorize("@ss.hasPermission('zc:sales-order:update')")
    public CommonResult<Boolean> cutProduct(@RequestBody @Valid ZcCutProductReqVO reqVO) {
        salesOrderProductService.cutProduct(reqVO);
        return success(true);
    }

    /**
     * 撤销面料单产品行裁剪（回退批次库存、清空裁剪数量、写入撤销裁剪记录）
     */
    @PutMapping("/cancel-cut")
    @Operation(summary = "撤销面料单产品行裁剪（回退批次库存、清空裁剪数量、写入撤销裁剪记录）")
    @PreAuthorize("@ss.hasPermission('zc:sales-order:update')")
    public CommonResult<Boolean> cancelCutProduct(@RequestBody @Valid ZcCancelCutProductReqVO reqVO) {
        salesOrderProductService.cancelCutProduct(reqVO);
        return success(true);
    }

    /**
     * 面料单产品行发货（标记已发货、记录发货时间、联动更新订单状态）
     */
    @PutMapping("/ship")
    @Operation(summary = "面料单产品行发货（标记已发货、记录发货时间、联动更新订单状态）")
    @PreAuthorize("@ss.hasPermission('zc:sales-order:update')")
    public CommonResult<Boolean> shipProduct(@RequestBody @Valid ZcShipProductReqVO reqVO) {
        salesOrderProductService.shipProduct(reqVO);
        return success(true);
    }

    /**
     * 撤销面料单产品行发货（回退发货状态、清空发货时间、联动更新订单状态）
     */
    @PutMapping("/cancel-ship")
    @Operation(summary = "撤销面料单产品行发货（回退发货状态、清空发货时间、联动更新订单状态）")
    @PreAuthorize("@ss.hasPermission('zc:sales-order:update')")
    public CommonResult<Boolean> cancelShipProduct(@RequestBody @Valid ZcCancelShipProductReqVO reqVO) {
        salesOrderProductService.cancelShipProduct(reqVO);
        return success(true);
    }

}
