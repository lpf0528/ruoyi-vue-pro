package cn.iocoder.yudao.module.quiz.controller.admin.project.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 项目分页 Request VO")
@Data
public class QuizProjectPageReqVO extends PageParam {

    @Schema(description = "名称", example = "李四")
    private String name;

    @Schema(description = "状态", example = "2")
    private Integer status;

    @Schema(description = "是否热门(小程序)")
    private Boolean recommendHot;

    @Schema(description = "是否轮播图(小程序)")
    private Boolean recommendBanner;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}