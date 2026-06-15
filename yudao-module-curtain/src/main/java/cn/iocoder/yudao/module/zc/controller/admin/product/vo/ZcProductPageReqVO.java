package cn.iocoder.yudao.module.zc.controller.admin.product.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 产品分页 Request VO")
@Data
public class ZcProductPageReqVO extends PageParam {

    @Schema(description = "名称", example = "张三")
    private String name;

    @Schema(description = "版本", example = "6")
    private Long versionId;

    @Schema(description = "供应商", example = "25473")
    private Long supplierId;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}