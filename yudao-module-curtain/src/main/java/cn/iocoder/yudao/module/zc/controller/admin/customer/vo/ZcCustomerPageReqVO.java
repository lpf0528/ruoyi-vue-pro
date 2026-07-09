package cn.iocoder.yudao.module.zc.controller.admin.customer.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 客户资料分页 Request VO")
@Data
public class ZcCustomerPageReqVO extends PageParam {

    @Schema(description = "简称", example = "张三")
    private String shortName;

    @Schema(description = "全称", example = "王五")
    private String name;

    @Schema(description = "物流", example = "429")
    private Long logisticId;

    @Schema(description = "关联品牌", example = "22168")
    private Long brandId;

}