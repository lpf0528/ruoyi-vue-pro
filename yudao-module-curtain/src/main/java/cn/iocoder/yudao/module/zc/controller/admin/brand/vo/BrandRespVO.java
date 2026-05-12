package cn.iocoder.yudao.module.zc.controller.admin.brand.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import cn.idev.excel.annotation.*;

@Schema(description = "管理后台 - 品牌 Response VO")
@Data
@ExcelIgnoreUnannotated
public class BrandRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "21719")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @ExcelProperty("名称")
    private String name;

    @Schema(description = "Logo URL")
    @ExcelProperty("Logo URL")
    private String logo;

    @Schema(description = "电话")
    @ExcelProperty("电话")
    private String mobile;

    @Schema(description = "地址")
    @ExcelProperty("地址")
    private String address;

    @Schema(description = "备注")
    @ExcelProperty("备注")
    private String note;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}