package cn.iocoder.yudao.module.zc.controller.admin.balance;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.zc.dal.dataobject.base.ZcCustomerBalanceLogDO;
import cn.iocoder.yudao.module.zc.service.balance.ZcCustomerBalanceLogService;
import cn.iocoder.yudao.module.zc.controller.admin.vo.balance.ZcCustomerBalanceLogPageReqVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 智仓客户余额流水")
@RestController
@RequestMapping("/zc/customer-balance-log")
@Validated
public class ZcCustomerBalanceLogController {

    @Resource
    private ZcCustomerBalanceLogService customerBalanceLogService;

    @GetMapping("/page")
    @Operation(summary = "余额流水分页")
    @PreAuthorize("@ss.hasPermission('zc:customer-balance-log:query')")
    public CommonResult<PageResult<ZcCustomerBalanceLogDO>> page(@Valid ZcCustomerBalanceLogPageReqVO pageReqVO) {
        return success(customerBalanceLogService.getBalanceLogPage(pageReqVO));
    }

}
