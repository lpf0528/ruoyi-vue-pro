package cn.iocoder.yudao.module.zc.controller.admin.base;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.zc.controller.admin.vo.base.ZcCustomerPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.base.ZcCustomerRespVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.base.ZcCustomerSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.base.ZcCustomerDO;
import cn.iocoder.yudao.module.zc.service.base.ZcCustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 智仓客户")
@RestController
@RequestMapping("/zc/customer")
@Validated
public class ZcCustomerController {

    @Resource
    private ZcCustomerService customerService;

    @PostMapping("/create")
    @Operation(summary = "创建客户")
    @PreAuthorize("@ss.hasPermission('zc:customer:create')")
    public CommonResult<Long> createCustomer(@Valid @RequestBody ZcCustomerSaveReqVO createReqVO) {
        return success(customerService.createCustomer(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新客户")
    @PreAuthorize("@ss.hasPermission('zc:customer:update')")
    public CommonResult<Boolean> updateCustomer(@Valid @RequestBody ZcCustomerSaveReqVO updateReqVO) {
        customerService.updateCustomer(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除客户")
    @PreAuthorize("@ss.hasPermission('zc:customer:delete')")
    public CommonResult<Boolean> deleteCustomer(@RequestParam("id") Long id) {
        customerService.deleteCustomer(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得客户")
    @PreAuthorize("@ss.hasPermission('zc:customer:query')")
    public CommonResult<ZcCustomerRespVO> getCustomer(@RequestParam("id") Long id) {
        ZcCustomerDO d = customerService.getCustomer(id);
        return success(BeanUtils.toBean(d, ZcCustomerRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "客户分页")
    @PreAuthorize("@ss.hasPermission('zc:customer:query')")
    public CommonResult<PageResult<ZcCustomerRespVO>> getCustomerPage(@Valid ZcCustomerPageReqVO pageReqVO) {
        PageResult<ZcCustomerDO> pageResult = customerService.getCustomerPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ZcCustomerRespVO.class));
    }

}
