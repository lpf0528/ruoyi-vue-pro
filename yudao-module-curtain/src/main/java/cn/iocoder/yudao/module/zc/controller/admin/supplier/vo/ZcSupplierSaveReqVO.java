package cn.iocoder.yudao.module.zc.controller.admin.supplier.vo;

import com.mzt.logapi.starter.annotation.DiffLogField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 供应商新增/修改 Request VO")
@Data
public class ZcSupplierSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "27125")
    private Long id;

    @Schema(description = "简称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @DiffLogField(name = "简称")
    @NotEmpty(message = "简称不能为空")
    private String shortName;

    @Schema(description = "全称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @DiffLogField(name = "全称")
    @NotEmpty(message = "全称不能为空")
    private String name;

    @Schema(description = "备注")
    @DiffLogField(name = "备注")
    private String note;

}
