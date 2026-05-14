package cn.iocoder.yudao.module.zc.controller.admin.productcategory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import cn.idev.excel.annotation.*;

@Schema(description = "管理后台 - 产品类别 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ProductCategoryRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "10243")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "类别名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("类别名称")
    private String value;

    @Schema(description = "备注")
    @ExcelProperty("备注")
    private String note;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}