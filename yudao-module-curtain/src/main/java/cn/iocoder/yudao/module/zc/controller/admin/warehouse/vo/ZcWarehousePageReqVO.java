package cn.iocoder.yudao.module.zc.controller.admin.warehouse.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 仓库分页 Request VO")
@Data
public class ZcWarehousePageReqVO extends PageParam {

    @Schema(description = "仓库名称", example = "张三")
    private String name;

    @Schema(description = "负责人", example = "29011")
    private Long managerId;

}