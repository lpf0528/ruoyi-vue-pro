package cn.iocoder.yudao.module.zc.controller.admin.curtainseries.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import cn.idev.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 窗帘系列 Response VO")
@Data
@ExcelIgnoreUnannotated
public class CurtainSeriesRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "1684")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "系列名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @ExcelProperty("系列名称")
    private String name;

    @Schema(description = "0窗帘 1软装 2罗马帘 3百叶帘", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "0窗帘 1软装 2罗马帘 3百叶帘", converter = DictConvert.class)
    @DictFormat("curtain_category") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer category;

    @Schema(description = "备注")
    @ExcelProperty("备注")
    private String note;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}