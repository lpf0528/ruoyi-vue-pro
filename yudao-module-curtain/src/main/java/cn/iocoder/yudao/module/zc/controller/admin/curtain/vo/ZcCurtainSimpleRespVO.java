package cn.iocoder.yudao.module.zc.controller.admin.curtain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Schema(description = "管理后台 - 窗帘精简 Response VO")
@Data
@Accessors(chain = true)
public class ZcCurtainSimpleRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "款式名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "默认褶倍", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal pleatRatioValue;

    @Schema(description = "褶距", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal pleatsDistance;

}
