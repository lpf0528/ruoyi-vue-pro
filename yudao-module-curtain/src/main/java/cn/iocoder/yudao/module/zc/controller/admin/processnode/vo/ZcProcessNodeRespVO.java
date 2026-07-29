package cn.iocoder.yudao.module.zc.controller.admin.processnode.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import cn.idev.excel.annotation.*;

@Schema(description = "管理后台 - 工序节点配置 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ZcProcessNodeRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "29873")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "工序名称，如：备料、裁剪、缝制、定型、质检、包装", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @ExcelProperty("工序名称，如：备料、裁剪、缝制、定型、质检、包装")
    private String name;

    @Schema(description = "排序号，数字越小越靠前", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("排序号，数字越小越靠前")
    private Integer sort;

    @Schema(description = "工序描述/操作说明", example = "随便")
    @ExcelProperty("工序描述/操作说明")
    private String description;

    @Schema(description = "分组：0=系统配置，1=手工配置", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("分组")
    private Integer group;

    @Schema(description = "创建者", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建者")
    private String creator;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "关联组件编号列表")
    private List<Long> elementIds;

    @Schema(description = "关联组件名称列表")
    @ExcelProperty("关联组件")
    private List<String> elementNames;

}