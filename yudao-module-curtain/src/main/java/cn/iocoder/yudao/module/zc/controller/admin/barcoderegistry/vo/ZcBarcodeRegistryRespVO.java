package cn.iocoder.yudao.module.zc.controller.admin.barcoderegistry.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 管理后台 - 码注册表响应 VO
 *
 * @author 智仓
 */
@Schema(description = "管理后台 - 码注册表响应")
@Data
public class ZcBarcodeRegistryRespVO {

    @Schema(description = "主键ID", example = "1")
    private Long id;

    @Schema(description = "码唯一ID（UUID），用于生成二维码", example = "550e8400-e29b-41d4-a716-446655440000")
    private String codeId;

    @Schema(description = "码类型", example = "PROCESS_QR")
    private String codeType;

    @Schema(description = "扫码后跳转路由", example = "/pages/process/scan")
    private String targetRoute;

    @Schema(description = "二维码原始内容（JSON）", example = "{\"orderId\":123}")
    private String codeContent;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

}
