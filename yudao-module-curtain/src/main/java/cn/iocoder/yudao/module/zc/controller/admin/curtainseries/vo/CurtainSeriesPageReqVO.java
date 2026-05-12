package cn.iocoder.yudao.module.zc.controller.admin.curtainseries.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 窗帘系列分页 Request VO")
@Data
public class CurtainSeriesPageReqVO extends PageParam {

    @Schema(description = "系列名称", example = "王五")
    private String name;

    @Schema(description = "0窗帘 1软装 2罗马帘 3百叶帘")
    private Integer category;

    @Schema(description = "备注")
    private String note;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}