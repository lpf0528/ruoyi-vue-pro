package cn.iocoder.yudao.module.zc.controller.admin.warehouse.vo;

import cn.iocoder.yudao.module.system.framework.operatelog.core.AdminUserParseFunction;
import com.mzt.logapi.starter.annotation.DiffLogField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 仓库新增/修改 Request VO")
@Data
public class ZcWarehouseSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "16525")
    private Long id;

    @Schema(description = "仓库名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @DiffLogField(name = "仓库名称")
    @NotEmpty(message = "仓库名称不能为空")
    private String name;

    @Schema(description = "负责人", example = "29011")
    @DiffLogField(name = "负责人", function = AdminUserParseFunction.NAME)
    private Long managerId;

    @Schema(description = "备注")
    @DiffLogField(name = "备注")
    private String note;

    @Schema(description = "是否默认仓库", example = "false")
    @DiffLogField(name = "是否默认")
    private Boolean defaultStatus;

}
