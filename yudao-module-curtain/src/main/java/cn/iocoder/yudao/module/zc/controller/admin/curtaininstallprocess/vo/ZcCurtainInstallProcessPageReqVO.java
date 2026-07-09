package cn.iocoder.yudao.module.zc.controller.admin.curtaininstallprocess.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 安装工艺分页 Request VO")
@Data
public class ZcCurtainInstallProcessPageReqVO extends PageParam {

    @Schema(description = "工艺名称", example = "芋艿")
    private String name;

}