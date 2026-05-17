package cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 成品订单-窗帘行分页 Request VO")
@Data
public class ZcSalesOrderCurtainPageReqVO extends PageParam {

    @Schema(description = "销售单", example = "13858")
    private Long orderId;

    @Schema(description = "款式", example = "26707")
    private Long curtainId;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}