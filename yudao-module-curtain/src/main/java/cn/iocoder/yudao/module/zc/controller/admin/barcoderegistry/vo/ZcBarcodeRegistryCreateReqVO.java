package cn.iocoder.yudao.module.zc.controller.admin.barcoderegistry.vo;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.IOException;

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

    @Schema(description = "二维码原始内容，可传 JSON 字符串或 JSON 对象，均存为 JSON 字符串", requiredMode = Schema.RequiredMode.REQUIRED, example = "{\"orderId\":123}")
    @NotBlank(message = "二维码内容不能为空")
    @JsonDeserialize(using = ZcBarcodeRegistryCreateReqVO.JsonObjectToStringDeserializer.class)
    private String codeContent;

    /**
     * 允许前端将 codeContent 传为 JSON 对象或 JSON 字符串，统一反序列化为字符串存储
     */
    static class JsonObjectToStringDeserializer extends JsonDeserializer<String> {
        @Override
        public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            // 若前端传的是字符串直接返回，若传的是 JSON 对象/数组则序列化为 JSON 字符串
            if (p.currentToken().isScalarValue()) {
                return p.getValueAsString();
            }
            return p.readValueAsTree().toString();
        }
    }

}
