package cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import cn.idev.excel.annotation.*;

@Schema(description = "管理后台 - 成品订单-结构 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ZcSalesOrderStructureRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "6698")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "销售单", requiredMode = Schema.RequiredMode.REQUIRED, example = "23383")
    @ExcelProperty("销售单")
    private Long orderId;

    @Schema(description = "窗帘行", requiredMode = Schema.RequiredMode.REQUIRED, example = "12566")
    @ExcelProperty("窗帘行")
    private Long orderCurtainId;

    @Schema(description = "结构", requiredMode = Schema.RequiredMode.REQUIRED, example = "17209")
    @ExcelProperty("结构")
    private Long structureId;

    @Schema(description = "高")
    @ExcelProperty("高")
    private BigDecimal height;

    @Schema(description = "宽")
    @ExcelProperty("宽")
    private BigDecimal width;

    @Schema(description = "左转角")
    @ExcelProperty("左转角")
    private String leftCorner;

    @Schema(description = "右转角")
    @ExcelProperty("右转角")
    private String rightCorner;

    @Schema(description = "粘贴方向")
    @ExcelProperty("粘贴方向")
    private String pasteDirection;

    @Schema(description = "安装工艺", example = "5095")
    @ExcelProperty("安装工艺")
    private Long installProcessId;

    @Schema(description = "打开方式")
    @ExcelProperty("打开方式")
    private String openMethod;

    @Schema(description = "加工类型", example = "1")
    @ExcelProperty("加工类型")
    private String processType;

    @Schema(description = "是否定型")
    @ExcelProperty("是否定型")
    private Boolean shaping;

    @Schema(description = "总褶数")
    @ExcelProperty("总褶数")
    private Integer pleatsNum;

    @Schema(description = "褶距")
    @ExcelProperty("褶距")
    private BigDecimal pleatsDistance;

    @Schema(description = "裙摆高度")
    @ExcelProperty("裙摆高度")
    private BigDecimal skirtHeight;

    @Schema(description = "备注")
    @ExcelProperty("备注")
    private String note;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}