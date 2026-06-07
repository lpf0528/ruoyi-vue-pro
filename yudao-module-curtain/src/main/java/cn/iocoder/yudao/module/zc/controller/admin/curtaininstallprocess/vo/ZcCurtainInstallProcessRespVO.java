package cn.iocoder.yudao.module.zc.controller.admin.curtaininstallprocess.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import cn.idev.excel.annotation.*;

@Schema(description = "管理后台 - 安装工艺 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ZcCurtainInstallProcessRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "1614")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "工艺名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @ExcelProperty("工艺名称")
    private String name;

    @Schema(description = "备注")
    @ExcelProperty("备注")
    private String note;

    @Schema(description = "关联工序节点 ID 列表", example = "[1, 2, 3]")
    @ExcelIgnore
    private List<Long> nodeIds;

    @Schema(description = "创建者")
    @ExcelProperty("创建者")
    private String creator;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}