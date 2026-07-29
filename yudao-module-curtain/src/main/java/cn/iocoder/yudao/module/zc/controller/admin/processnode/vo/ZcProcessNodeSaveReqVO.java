package cn.iocoder.yudao.module.zc.controller.admin.processnode.vo;

import com.mzt.logapi.starter.annotation.DiffLogField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 工序节点配置新增/修改 Request VO")
@Data
public class ZcProcessNodeSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "29873")
    private Long id;

    @Schema(description = "工序名称，如：备料、裁剪、缝制、定型、质检、包装", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @DiffLogField(name = "工序名称")
    @NotEmpty(message = "工序名称，如：备料、裁剪、缝制、定型、质检、包装不能为空")
    private String name;

    @Schema(description = "排序号，数字越小越靠前", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @DiffLogField(name = "排序号")
    private Integer sort;

    @Schema(description = "工序描述/操作说明", example = "随便")
    @DiffLogField(name = "工序描述")
    private String description;

    @Schema(description = "关联组件编号列表")
    private List<Long> elementIds;

}
