package cn.iocoder.yudao.module.zc.controller.admin.productspec.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 产品规格新增/修改 Request VO")
@Data
public class zcProductSpecSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "20347")
    private Long id;

    @Schema(description = "规格值", requiredMode = Schema.RequiredMode.REQUIRED, example = "2.5")
    @NotEmpty(message = "规格值不能为空")
    private String value;

    @Schema(description = "备注")
    private String note;

}