package cn.iocoder.yudao.module.zc.controller.admin.curtainpleatratio.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import cn.idev.excel.annotation.*;

@Schema(description = "管理后台 - 褶倍 Response VO")
@Data
@ExcelIgnoreUnannotated
public class CurtainPleatRatioRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "26473")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "褶倍", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("褶倍")
    private BigDecimal value;

    @Schema(description = "排序")
    @ExcelProperty("排序")
    private Integer rank;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}