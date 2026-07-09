package cn.iocoder.yudao.module.zc.controller.admin.productcategory.vo;

import com.mzt.logapi.starter.annotation.DiffLogField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 产品类别新增/修改 Request VO")
@Data
public class ZcProductCategorySaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "10243")
    private Long id;

    @Schema(description = "类别名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @DiffLogField(name = "类别名称")
    @NotEmpty(message = "类别名称不能为空")
    private String value;

    @Schema(description = "备注")
    @DiffLogField(name = "备注")
    private String note;

}
