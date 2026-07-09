package cn.iocoder.yudao.module.zc.controller.admin.curtainpleatratio.vo;

import lombok.Data;
import lombok.experimental.Accessors;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "管理后台 - 褶倍精简 Response VO")
@Data
@Accessors(chain = true)
public class ZcCurtainPleatRatioSimpleRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "褶倍", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal value;

}
