package cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 成品订单-结构新增/修改 Request VO")
@Data
public class ZcSalesOrderStructureSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "6698")
    private Long id;

    @Schema(description = "销售单", requiredMode = Schema.RequiredMode.REQUIRED, example = "23383")
    @NotNull(message = "销售单不能为空")
    private Long orderId;

    @Schema(description = "窗帘行", requiredMode = Schema.RequiredMode.REQUIRED, example = "12566")
    @NotNull(message = "窗帘行不能为空")
    private Long orderCurtainId;

    @Schema(description = "结构", requiredMode = Schema.RequiredMode.REQUIRED, example = "17209")
    @NotNull(message = "结构不能为空")
    private Long structureId;

    @Schema(description = "高")
    private BigDecimal height;

    @Schema(description = "宽")
    private BigDecimal width;

    @Schema(description = "左转角")
    private String leftCorner;

    @Schema(description = "右转角")
    private String rightCorner;

    @Schema(description = "粘贴方向")
    private String pasteDirection;

    @Schema(description = "安装工艺", example = "5095")
    private Long installProcessId;

    @Schema(description = "打开方式")
    private String openMethod;

    @Schema(description = "加工类型", example = "1")
    private String processType;

    @Schema(description = "是否定型")
    private Boolean shaping;

    @Schema(description = "总褶数")
    private Integer pleatsNum;

    @Schema(description = "褶距")
    private BigDecimal pleatsDistance;

    @Schema(description = "裙摆高度")
    private BigDecimal skirtHeight;

    @Schema(description = "备注")
    private String note;

}