package cn.iocoder.yudao.module.zc.controller.admin.curtain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 窗帘新增/修改 Request VO")
@Data
public class CurtainSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "21476")
    private Long id;

    @Schema(description = "款式名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @NotEmpty(message = "款式名称不能为空")
    private String name;

    @Schema(description = "系列", requiredMode = Schema.RequiredMode.REQUIRED, example = "19400")
    @NotNull(message = "系列不能为空")
    private Long seriesId;

    @Schema(description = "粘贴方向")
    private String pasteDirection;

    @Schema(description = "打开方式")
    private String openMethod;

    @Schema(description = "默认安装工艺", example = "20546")
    private Long installProcessId;

    @Schema(description = "加工类型", example = "2")
    private String processType;

    @Schema(description = "默认褶倍")
    private BigDecimal pleatRatioValue;

    @Schema(description = "褶距")
    private BigDecimal pleatsDistance;

    @Schema(description = "备注")
    private String note;

}