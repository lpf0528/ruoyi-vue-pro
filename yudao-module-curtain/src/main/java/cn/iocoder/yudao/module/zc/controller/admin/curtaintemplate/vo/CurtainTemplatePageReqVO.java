package cn.iocoder.yudao.module.zc.controller.admin.curtaintemplate.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 窗帘模板分页 Request VO")
@Data
public class CurtainTemplatePageReqVO extends PageParam {

    @Schema(description = "款式", example = "9997")
    private Long curtainId;

    @Schema(description = "结构", example = "25411")
    private Long structureId;

    @Schema(description = "配件", example = "32517")
    private Long elementId;

    @Schema(description = "单位", example = "15552")
    private Long unitId;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}