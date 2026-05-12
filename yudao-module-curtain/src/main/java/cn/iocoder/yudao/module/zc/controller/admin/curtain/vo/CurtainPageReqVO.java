package cn.iocoder.yudao.module.zc.controller.admin.curtain.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 窗帘分页 Request VO")
@Data
public class CurtainPageReqVO extends PageParam {

    @Schema(description = "款式名称", example = "赵六")
    private String name;

    @Schema(description = "系列", example = "19400")
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

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}