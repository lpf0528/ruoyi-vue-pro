package cn.iocoder.yudao.module.zc.controller.admin.curtainstructure.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 窗帘结构分页 Request VO")
@Data
public class ZcCurtainStructurePageReqVO extends PageParam {

    @Schema(description = "结构名称", example = "赵六")
    private String name;


}