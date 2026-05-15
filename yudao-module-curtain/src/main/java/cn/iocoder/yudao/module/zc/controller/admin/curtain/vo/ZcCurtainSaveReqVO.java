package cn.iocoder.yudao.module.zc.controller.admin.curtain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 窗帘新增/修改 Request VO")
@Data
public class ZcCurtainSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "2965")
    private Long id;

    @Schema(description = "款式名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @NotEmpty(message = "款式名称不能为空")
    private String name;

    @Schema(description = "默认褶倍")
    private BigDecimal pleatRatioValue;

    @Schema(description = "褶距")
    private BigDecimal pleatsDistance;

    @Schema(description = "备注")
    private String note;

}