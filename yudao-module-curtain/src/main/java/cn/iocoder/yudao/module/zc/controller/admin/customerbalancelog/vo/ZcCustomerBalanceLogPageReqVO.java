package cn.iocoder.yudao.module.zc.controller.admin.customerbalancelog.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 客户余额变动流水分页 Request VO")
@Data
public class ZcCustomerBalanceLogPageReqVO extends PageParam {

    @Schema(description = "客户", example = "7166")
    private Long customerId;

    @Schema(description = "余额变动额")
    private BigDecimal changeAmount;

    @Schema(description = "变动前余额")
    private BigDecimal balanceBefore;

    @Schema(description = "变动后余额")
    private BigDecimal balanceAfter;

    @Schema(description = "业务类型", example = "1")
    private String bizType;

    @Schema(description = "关联单据类型", example = "1")
    private String refType;

    @Schema(description = "关联单据主键", example = "27872")
    private Long refId;

    @Schema(description = "关联单号快照")
    private String refNo;

    @Schema(description = "备注", example = "你猜")
    private String remark;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}