package cn.iocoder.yudao.module.zc.controller.admin.logistics.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 物流公司分页 Request VO")
@Data
public class ZcLogisticsPageReqVO extends PageParam {

    @Schema(description = "编码", example = "shunfeng")
    private String code;

    @Schema(description = "名称", example = "顺丰快递")
    private String name;

    @Schema(description = "联系人", example = "王五")
    private String contactName;

}