package cn.iocoder.yudao.module.zc.controller.admin.brand.vo;

import com.mzt.logapi.starter.annotation.DiffLogField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 品牌新增/修改 Request VO")
@Data
public class ZcBrandSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "21719")
    private Long id;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @DiffLogField(name = "名称")
    @NotEmpty(message = "名称不能为空")
    private String name;

    @Schema(description = "Logo URL")
    @DiffLogField(name = "Logo URL")
    private String logo;

    @Schema(description = "电话")
    @DiffLogField(name = "电话")
    private String mobile;

    @Schema(description = "地址")
    @DiffLogField(name = "地址")
    private String address;

    @Schema(description = "备注")
    @DiffLogField(name = "备注")
    private String note;

    @Schema(description = "是否默认")
    @DiffLogField(name = "是否默认")
    private Boolean isDefault;

}
