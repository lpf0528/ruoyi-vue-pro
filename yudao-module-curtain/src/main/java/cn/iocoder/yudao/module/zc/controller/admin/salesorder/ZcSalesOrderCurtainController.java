package cn.iocoder.yudao.module.zc.controller.admin.salesorder;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZcSalesOrderCurtainDO;
import cn.iocoder.yudao.module.zc.service.salesorder.ZcSalesOrderCurtainService;

@Tag(name = "管理后台 - 成品订单-窗帘行")
@RestController
@RequestMapping("/zc/sales-order-curtain")
@Validated
public class ZcSalesOrderCurtainController {

    @Resource
    private ZcSalesOrderCurtainService salesOrderCurtainService;

    @GetMapping("/get")
    @Operation(summary = "获得成品订单-窗帘行")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('zc:sales-order-curtain:query')")
    public CommonResult<ZcSalesOrderCurtainRespVO> getSalesOrderCurtain(@RequestParam("id") Long id) {
        ZcSalesOrderCurtainDO salesOrderCurtain = salesOrderCurtainService.getSalesOrderCurtain(id);
        return success(BeanUtils.toBean(salesOrderCurtain, ZcSalesOrderCurtainRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得成品订单-窗帘行分页")
    @PreAuthorize("@ss.hasPermission('zc:sales-order-curtain:query')")
    public CommonResult<PageResult<ZcSalesOrderCurtainRespVO>> getSalesOrderCurtainPage(@Valid ZcSalesOrderCurtainPageReqVO pageReqVO) {
        PageResult<ZcSalesOrderCurtainDO> pageResult = salesOrderCurtainService.getSalesOrderCurtainPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ZcSalesOrderCurtainRespVO.class));
    }

}
