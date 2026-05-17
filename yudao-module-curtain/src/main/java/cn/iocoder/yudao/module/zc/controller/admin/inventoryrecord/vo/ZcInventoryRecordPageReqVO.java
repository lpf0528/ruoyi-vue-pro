package cn.iocoder.yudao.module.zc.controller.admin.inventoryrecord.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;

@Schema(description = "管理后台 - 盘点记录分页 Request VO")
@Data
public class ZcInventoryRecordPageReqVO extends PageParam {

    @Schema(description = "产品", example = "9127")
    private Long productId;

    @Schema(description = "产品名称")
    private String productName;

    @Schema(description = "版本ID")
    private Long versionId;

    @Schema(description = "批次", example = "8051")
    private Long batchId;

    @Schema(description = "批次号")
    private String batchNo;

    @Schema(description = "仓库ID")
    private Long warehouseId;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    private LocalDate[] createTime;

}