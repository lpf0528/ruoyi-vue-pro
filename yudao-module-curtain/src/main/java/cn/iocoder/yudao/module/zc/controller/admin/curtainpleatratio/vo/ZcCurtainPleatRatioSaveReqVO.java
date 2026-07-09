package cn.iocoder.yudao.module.zc.controller.admin.curtainpleatratio.vo;

import com.mzt.logapi.starter.annotation.DiffLogField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 褶倍新增/修改 Request VO")
@Data
public class ZcCurtainPleatRatioSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "26473")
    private Long id;

    @Schema(description = "褶倍", requiredMode = Schema.RequiredMode.REQUIRED)
    @DiffLogField(name = "褶倍")
    @NotNull(message = "褶倍不能为空")
    private BigDecimal value;

    @Schema(description = "排序")
    @DiffLogField(name = "排序")
    private Integer rank;

}
