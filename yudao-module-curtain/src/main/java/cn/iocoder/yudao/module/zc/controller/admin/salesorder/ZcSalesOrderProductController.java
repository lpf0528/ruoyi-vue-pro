package cn.iocoder.yudao.module.zc.controller.admin.salesorder;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
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
    @GetMapping("/get")
    @Operation(summary = "查询面料单详情（含产品批次行列表）")
    @Parameter(name = "id", description = "订单 ID", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('zc:sales-order:query')")
    public CommonResult<ZcSalesOrderProductDetailRespVO> getSalesOrderProductDetail(
            @RequestParam("id") Long id) {
        return success(salesOrderProductService.getSalesOrderProductDetail(id));
    }

}
