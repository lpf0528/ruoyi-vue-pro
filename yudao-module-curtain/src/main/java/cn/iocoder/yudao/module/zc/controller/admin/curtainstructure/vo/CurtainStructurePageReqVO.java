package cn.iocoder.yudao.module.zc.controller.admin.curtainstructure.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 窗帘结构部位分页 Request VO")
@Data
public class CurtainStructurePageReqVO extends PageParam {

    @Schema(description = "部位名称", example = "赵六")
    private String name;

    @Schema(description = "帘头/帘身/飘窗垫/其他", example = "1")
    private String type;

    @Schema(description = "备注")
    private String note;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}