package cn.iocoder.yudao.module.zc.controller.admin.curtaintemplate.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 窗帘模板新增/修改 Request VO")
@Data
public class ZcCurtainTemplateSaveReqVO {

    @Schema(description = "款式", requiredMode = Schema.RequiredMode.REQUIRED, example = "9997")
    @NotNull(message = "款式不能为空")
    private Long curtainId;

    @Schema(description = "结构列表", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "结构列表不能为空")
    private List<StructureItem> structures;

    @Schema(description = "结构项")
    @Data
    public static class StructureItem {

        @Schema(description = "结构", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "结构不能为空")
        private Long structureId;

        @Schema(description = "配件列表", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotEmpty(message = "配件列表不能为空")
        private List<ElementItem> elements;
    }

    @Schema(description = "配件项")
    @Data
    public static class ElementItem {

        @Schema(description = "配件", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "配件不能为空")
        private Long elementId;

        @Schema(description = "产品，可为空")
        private Long productId;
    }

}
