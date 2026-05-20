package cn.iocoder.yudao.module.zc.controller.admin.bills.vo;

import lombok.*;

import java.time.LocalDate;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 收支账单分页 Request VO")
@Data
public class ZcBillsPageReqVO extends PageParam {

    @Schema(description = "单号")
    private String billNo;

    @Schema(description = "付款时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDate[] billDate;

    @Schema(description = "财务人员", example = "25823")
    private Long billUserId;

    @Schema(description = "客户", example = "11545")
    private Long customerId;

    @Schema(description = "收支方式", example = "19006")
    private Long billMethodId;

}