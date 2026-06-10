package cn.iocoder.yudao.module.zc.controller.admin.curtainstructureelement.vo;

import com.mzt.logapi.starter.annotation.DiffLogField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 窗帘结构组件新增/修改 Request VO")
@Data
public class ZcCurtainStructureElementSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "15929")
    private Long id;

    @Schema(description = "组件名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @DiffLogField(name = "组件名称")
    @NotEmpty(message = "组件名称不能为空")
    private String name;

    @Schema(description = "备注")
    @DiffLogField(name = "备注")
    private String note;

    @Schema(description = "版本")
    @DiffLogField(name = "版本")
    private Long versionId;

    @Schema(description = "是否打印", example = "true")
    @DiffLogField(name = "是否打印")
    private Boolean isPrint;

}
