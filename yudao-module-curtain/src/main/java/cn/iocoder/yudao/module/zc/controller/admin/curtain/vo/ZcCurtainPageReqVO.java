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
public class ZcCurtainPageReqVO extends PageParam {

    @Schema(description = "款式名称", example = "李四")
    private String name;

}