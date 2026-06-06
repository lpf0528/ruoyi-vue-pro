package cn.iocoder.yudao.module.zc.controller.admin.salesorder;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.module.zc.service.salesorder.ZcSalesOrderCurtainService;

@Tag(name = "管理后台 - 成品订单-窗帘行")
@RestController
@RequestMapping("/zc/sales-order-curtain")
@Validated
public class ZcSalesOrderCurtainController {

    @Resource
    private ZcSalesOrderCurtainService salesOrderCurtainService;

    @PutMapping("/pack")
    @Operation(summary = "打包窗帘行（将窗帘行状态更新为已打包，并联动更新订单状态）")
    @Parameter(name = "id", description = "窗帘行 ID", required = true)
    @PreAuthorize("@ss.hasPermission('zc:sales-order-curtain:update')")
    public CommonResult<Boolean> packCurtain(@RequestParam("id") Long id) {
        salesOrderCurtainService.packCurtain(id);
        return success(true);
    }

}
