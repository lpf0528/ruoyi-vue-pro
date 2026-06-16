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
 * <p>提供以客户+版本为维度的规格授权价批量保存与查询能力。</p>
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
            description = "覆盖写语义：按 customerId+versionId 分组，删除旧记录后全量插入新记录")
    @PreAuthorize("@ss.hasPermission('zc:customer-version-spec-price:create')")
    public CommonResult<Boolean> batchSaveCustomerVersionSpecPrice(
            @Valid @RequestBody List<ZcCustomerVersionSpecPriceSaveReqVO> saveReqVOList) {
        customerVersionSpecPriceService.batchSaveCustomerVersionSpecPrice(saveReqVOList);
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
