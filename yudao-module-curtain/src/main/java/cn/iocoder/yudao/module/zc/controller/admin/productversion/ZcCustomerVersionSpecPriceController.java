package cn.iocoder.yudao.module.zc.controller.admin.productversion;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.zc.controller.admin.productversion.vo.ZcCustomerVersionSpecPriceRespVO;
import cn.iocoder.yudao.module.zc.controller.admin.productversion.vo.ZcCustomerVersionSpecPriceSaveReqVO;
import cn.iocoder.yudao.module.zc.service.productversion.ZcCustomerVersionSpecPriceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * 管理后台 - 客户版本销售授权价
 *
 * @author 01Coder
 */
@Tag(name = "管理后台 - 客户版本销售授权价")
@RestController
@RequestMapping("/zc/customer-version-spec-price")
@Validated
public class ZcCustomerVersionSpecPriceController {

    @Resource
    private ZcCustomerVersionSpecPriceService customerVersionSpecPriceService;

    @PostMapping("/batch-save")
    @Operation(summary = "批量保存客户版本规格授权价",
            description = "先物理删除该客户所有旧记录，再全量插入提交的数据；id 字段无需传入")
    @PreAuthorize("@ss.hasPermission('zc:customer-version-spec-price:create')")
    public CommonResult<Boolean> batchSaveCustomerVersionSpecPrice(
            @Valid @RequestBody ZcCustomerVersionSpecPriceSaveReqVO saveReqVO) {
        customerVersionSpecPriceService.batchSaveCustomerVersionSpecPrice(saveReqVO);
        return success(true);
    }

    @GetMapping("/list")
    @Operation(summary = "查询客户版本规格授权价列表")
    @Parameter(name = "customerId", description = "客户编号", required = true)
    @Parameter(name = "versionId", description = "产品版本编号")
    @PreAuthorize("@ss.hasPermission('zc:customer-version-spec-price:query')")
    public CommonResult<List<ZcCustomerVersionSpecPriceRespVO>> getCustomerVersionSpecPriceList(
            @RequestParam("customerId") @NotNull Long customerId,
            @RequestParam(value = "versionId", required = false) Long versionId) {
        return success(customerVersionSpecPriceService.getCustomerVersionSpecPriceList(customerId, versionId));
    }

}
