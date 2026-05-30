package cn.iocoder.yudao.module.zc.controller.admin.curtaintemplate.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "管理后台 - 获得窗帘模板 Response VO")
@Data
public class ZcCurtainTemplateGetRespVO {

    @Schema(description = "款式ID")
    private Long curtainId;

    @Schema(description = "结构列表")
    private List<StructureItem> structures;

    @Schema(description = "结构项")
    @Data
    public static class StructureItem {

        @Schema(description = "结构ID")
        private Long structureId;

        @Schema(description = "配件列表")
        private List<ElementItem> elements;
    }

    @Schema(description = "配件项")
    @Data
    public static class ElementItem {

        @Schema(description = "配件ID")
        private Long elementId;

        @Schema(description = "版本ID")
        @JsonProperty("version_id")
        private Long versionId;

        @Schema(description = "产品ID")
        private Long productId;
    }
}
