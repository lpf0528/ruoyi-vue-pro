package cn.iocoder.yudao.module.zc.controller.admin.curtain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import cn.idev.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 窗帘 Response VO")
@Data
@ExcelIgnoreUnannotated
public class CurtainRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "21476")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "款式名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @ExcelProperty("款式名称")
    private String name;

    @Schema(description = "系列", requiredMode = Schema.RequiredMode.REQUIRED, example = "19400")
    @ExcelProperty("系列")
    private Long seriesId;

    @Schema(description = "粘贴方向")
    @ExcelProperty(value = "粘贴方向", converter = DictConvert.class)
    @DictFormat("paste_direction") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String pasteDirection;

    @Schema(description = "打开方式")
    @ExcelProperty(value = "打开方式", converter = DictConvert.class)
    @DictFormat("open_method") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String openMethod;

    @Schema(description = "默认安装工艺", example = "20546")
    @ExcelProperty("默认安装工艺")
    private Long installProcessId;

    @Schema(description = "加工类型", example = "2")
    @ExcelProperty(value = "加工类型", converter = DictConvert.class)
    @DictFormat("process_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String processType;

    @Schema(description = "默认褶倍")
    @ExcelProperty("默认褶倍")
    private BigDecimal pleatRatioValue;

    @Schema(description = "褶距")
    @ExcelProperty("褶距")
    private BigDecimal pleatsDistance;

    @Schema(description = "备注")
    @ExcelProperty("备注")
    private String note;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}