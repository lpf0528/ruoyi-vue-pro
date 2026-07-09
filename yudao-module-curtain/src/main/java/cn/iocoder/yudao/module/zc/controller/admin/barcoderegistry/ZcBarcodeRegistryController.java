package cn.iocoder.yudao.module.zc.controller.admin.barcoderegistry;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.zc.controller.admin.barcoderegistry.vo.ZcBarcodeRegistryCreateReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.barcoderegistry.vo.ZcBarcodeRegistryRespVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.barcoderegistry.ZcBarcodeRegistryDO;
import cn.iocoder.yudao.module.zc.service.barcoderegistry.ZcBarcodeRegistryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * 管理后台 - 码注册表 Controller
 *
 * <p>提供二维码的生成注册与扫码查询接口，
 * 权限标识前缀 {@code zc:barcode-registry:*}</p>
 *
 * @author 智仓
 */
@Tag(name = "管理后台 - 码注册表")
@RestController
@RequestMapping("/zc/barcode-registry")
@Validated
public class ZcBarcodeRegistryController {

    @Resource
    private ZcBarcodeRegistryService barcodeRegistryService;

    @PostMapping("/create")
    @Operation(summary = "生成并注册二维码", description = "服务端自动生成 UUID 作为 codeId，返回 codeId 供前端生成二维码图片")
    @PreAuthorize("@ss.hasPermission('zc:barcode-registry:create')")
    public CommonResult<String> createBarcodeRegistry(@Valid @RequestBody ZcBarcodeRegistryCreateReqVO createReqVO) {
        return success(barcodeRegistryService.createBarcodeRegistry(createReqVO));
    }

    @GetMapping("/get")
    @Operation(summary = "根据码ID获取注册信息", description = "App 扫码后携带 codeId 调用，获取跳转路由和业务数据")
    @Parameter(name = "codeId", description = "码唯一ID（UUID）", required = true, example = "550e8400e29b41d4a716446655440000")
    @PreAuthorize("@ss.hasPermission('zc:barcode-registry:query')")
    public CommonResult<ZcBarcodeRegistryRespVO> getBarcodeRegistry(
            @RequestParam("codeId") @NotBlank(message = "码ID不能为空") String codeId) {
        ZcBarcodeRegistryDO registry = barcodeRegistryService.getBarcodeRegistry(codeId);
        return success(BeanUtils.toBean(registry, ZcBarcodeRegistryRespVO.class));
    }

}
