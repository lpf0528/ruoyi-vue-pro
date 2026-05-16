package cn.iocoder.yudao.module.zc.controller.admin.productbatch.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 产品批次分页 Request VO")
@Data
public class ZcProductBatchPageReqVO extends PageParam {

    @Schema(description = "批号")
    private String batchNo;

    @Schema(description = "入库日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDate[] inboundDate;

    @Schema(description = "产品", example = "7855")
    private Long productId;

    @Schema(description = "仓库", example = "5470")
    private Long warehouseId;

    @Schema(description = "供应商", example = "12241")
    private Long supplierId;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}