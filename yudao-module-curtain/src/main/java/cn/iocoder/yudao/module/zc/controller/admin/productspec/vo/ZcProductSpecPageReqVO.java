package cn.iocoder.yudao.module.zc.controller.admin.productspec.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 产品规格分页 Request VO")
@Data
public class ZcProductSpecPageReqVO extends PageParam {

    @Schema(description = "规格值", example = "2.5")
    private String value;

}