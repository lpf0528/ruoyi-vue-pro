package cn.iocoder.yudao.module.zc.controller.admin.productspec.vo;

import com.mzt.logapi.starter.annotation.DiffLogField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 产品规格新增/修改 Request VO")
@Data
public class ZcProductSpecSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "20347")
    private Long id;

    @Schema(description = "规格值", requiredMode = Schema.RequiredMode.REQUIRED, example = "2.5")
    @DiffLogField(name = "规格值")
    @NotEmpty(message = "规格值不能为空")
    private String value;

    @Schema(description = "备注")
    @DiffLogField(name = "备注")
    private String note;

}
