package cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 成品订单-结构分页 Request VO")
@Data
public class ZcSalesOrderStructurePageReqVO extends PageParam {

    @Schema(description = "销售单", example = "23383")
    private Long orderId;

    @Schema(description = "窗帘行", example = "12566")
    private Long orderCurtainId;

    @Schema(description = "结构", example = "17209")
    private Long structureId;

}