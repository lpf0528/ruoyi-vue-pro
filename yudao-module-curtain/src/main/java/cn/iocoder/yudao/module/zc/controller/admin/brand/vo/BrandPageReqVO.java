package cn.iocoder.yudao.module.zc.controller.admin.brand.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 品牌分页 Request VO")
@Data
public class BrandPageReqVO extends PageParam {

    @Schema(description = "名称", example = "王五")
    private String name;

    @Schema(description = "Logo URL")
    private String logo;

    @Schema(description = "电话")
    private String mobile;

    @Schema(description = "地址")
    private String address;

    @Schema(description = "备注")
    private String note;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}