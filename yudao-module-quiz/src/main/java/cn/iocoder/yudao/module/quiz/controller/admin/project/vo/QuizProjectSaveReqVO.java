package cn.iocoder.yudao.module.quiz.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 项目新增/修改 Request VO")
@Data
public class QuizProjectSaveReqVO {

    @Schema(description = "项目ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "14289")
    private Long id;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @NotEmpty(message = "名称不能为空")
    private String name;

    @Schema(description = "封面图片地址", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.iocoder.cn")
    @NotEmpty(message = "封面图片地址不能为空")
    private String picUrl;

    @Schema(description = "简介")
    private String introduction;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "状态不能为空")
    private Integer status;

    @Schema(description = "是否热门(小程序)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否热门(小程序)不能为空")
    private Boolean recommendHot;

    @Schema(description = "是否轮播图(小程序)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否轮播图(小程序)不能为空")
    private Boolean recommendBanner;

}