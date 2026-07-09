package cn.iocoder.yudao.module.zc.controller.admin.processnode.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 工序节点配置分页 Request VO")
@Data
public class ZcProcessNodePageReqVO extends PageParam {

    @Schema(description = "工序名称，如：备料、裁剪、缝制、定型、质检、包装", example = "芋艿")
    private String name;

    @Schema(description = "分组：0=系统配置，1=手工配置")
    private Integer group;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}