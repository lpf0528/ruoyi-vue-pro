package cn.iocoder.yudao.module.zc.controller.admin.salesorder;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.ZcSalesOrderProductCreateReqVO;
import cn.iocoder.yudao.module.zc.service.salesorder.ZcSalesOrderProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
