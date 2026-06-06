package cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import cn.idev.excel.annotation.*;

@Schema(description = "管理后台 - 成品订单-窗帘行 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ZcSalesOrderCurtainRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "3946")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "销售单", requiredMode = Schema.RequiredMode.REQUIRED, example = "13858")
    @ExcelProperty("销售单")
    private Long orderId;

    @Schema(description = "款式", example = "26707")
    @ExcelProperty("款式")
    private Long curtainId;

    @Schema(description = "房间")
    @ExcelProperty("房间")
    private String room;

    @Schema(description = "褶倍快照")
    @ExcelProperty("褶倍快照")
    private BigDecimal pleatRatioValue;

    @Schema(description = "折扣率")
    @ExcelProperty("折扣率")
    private BigDecimal discountRate;

    @Schema(description = "应收金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("应收金额")
    private BigDecimal amount;

    @Schema(description = "图片1")
    @ExcelProperty("图片1")
    private String image1;

    @Schema(description = "图片2")
    @ExcelProperty("图片2")
    private String image2;

    @Schema(description = "配件多选")
    @ExcelProperty("配件多选")
    private String mountings;

    @Schema(description = "备注")
    @ExcelProperty("备注")
    private String note;

    @Schema(description = "褶距")
    @ExcelProperty("褶距")
    private BigDecimal pleatsDistance;

    @Schema(description = "窗帘行状态，参见 zc_order_status 字典")
    @ExcelProperty("状态")
    private String status;

    @Schema(description = "序号，同一订单内窗帘行的显示顺序，从 1 开始")
    @ExcelProperty("序号")
    private Integer index;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}