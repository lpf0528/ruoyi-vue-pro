package cn.iocoder.yudao.module.zc.controller.admin.barcoderegistry.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 管理后台 - 码注册表创建请求 VO
 *
 * @author 智仓
 */
@Schema(description = "管理后台 - 码注册表创建请求")
@Data
public class ZcBarcodeRegistryCreateReqVO {

    @Schema(description = "码类型，如 INBOUND_QR / PROCESS_QR / LOCATION_QR", requiredMode = Schema.RequiredMode.REQUIRED, example = "PROCESS_QR")
    @NotBlank(message = "码类型不能为空")
    @Size(max = 32, message = "码类型长度不能超过 32 字符")
    private String codeType;

    @Schema(description = "扫码后跳转的路由路径", requiredMode = Schema.RequiredMode.REQUIRED, example = "/pages/process/scan")
    @NotBlank(message = "跳转路由不能为空")
    @Size(max = 128, message = "跳转路由长度不能超过 128 字符")
    private String targetRoute;

    @Schema(description = "二维码原始内容（JSON 格式）", requiredMode = Schema.RequiredMode.REQUIRED, example = "{\"orderId\":123}")
    @NotBlank(message = "二维码内容不能为空")
    private String codeContent;

}
