package cn.iocoder.yudao.module.zc.controller.admin.base;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.zc.controller.admin.vo.base.ZcPaymentPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.base.ZcPaymentSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.base.ZcPaymentDO;
import cn.iocoder.yudao.module.zc.service.base.ZcPaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 智仓收款方式")
@RestController
@RequestMapping("/zc/payment")
@Validated
public class ZcPaymentController {

    @Resource
    private ZcPaymentService paymentService;

    @PostMapping("/create")
    @Operation(summary = "创建收款方式")
    @PreAuthorize("@ss.hasPermission('zc:payment:create')")
    public CommonResult<Long> create(@Valid @RequestBody ZcPaymentSaveReqVO reqVO) {
        return success(paymentService.create(reqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新收款方式")
    @PreAuthorize("@ss.hasPermission('zc:payment:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody ZcPaymentSaveReqVO reqVO) {
        paymentService.update(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除收款方式")
    @PreAuthorize("@ss.hasPermission('zc:payment:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        paymentService.delete(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得收款方式")
    @PreAuthorize("@ss.hasPermission('zc:payment:query')")
    public CommonResult<ZcPaymentSaveReqVO> get(@RequestParam("id") Long id) {
        return success(BeanUtils.toBean(paymentService.get(id), ZcPaymentSaveReqVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "收款方式分页")
    @PreAuthorize("@ss.hasPermission('zc:payment:query')")
    public CommonResult<PageResult<ZcPaymentSaveReqVO>> page(@Valid ZcPaymentPageReqVO pageReqVO) {
        PageResult<ZcPaymentDO> pageResult = paymentService.getPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ZcPaymentSaveReqVO.class));
    }

}
