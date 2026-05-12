package cn.iocoder.yudao.module.zc.controller.admin.curtainstructure.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import cn.idev.excel.annotation.*;

@Schema(description = "管理后台 - 窗帘结构部位 Response VO")
@Data
@ExcelIgnoreUnannotated
public class CurtainStructureRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "10163")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "部位名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @ExcelProperty("部位名称")
    private String name;

    @Schema(description = "帘头/帘身/飘窗垫/其他", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("帘头/帘身/飘窗垫/其他")
    private String type;

    @Schema(description = "备注")
    @ExcelProperty("备注")
    private String note;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}