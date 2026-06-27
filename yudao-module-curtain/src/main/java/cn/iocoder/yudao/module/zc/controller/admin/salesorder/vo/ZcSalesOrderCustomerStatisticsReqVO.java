package cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 管理后台 - 销售订单按客户统计 Request VO
 */
@Schema(description = "管理后台 - 销售订单按客户统计 Request VO")
@Data
public class ZcSalesOrderCustomerStatisticsReqVO {

    @Schema(description = "确认时间范围（起止时间，含边界）", requiredMode = Schema.RequiredMode.REQUIRED,
            example = "[\"2026-01-01 00:00:00\", \"2026-01-31 23:59:59\"]")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @NotNull(message = "确认时间范围不能为空")
    @Size(min = 2, max = 2, message = "确认时间范围必须包含起止两个时间点")
    private LocalDateTime[] confirmTime;

}
