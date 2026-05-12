package cn.iocoder.yudao.module.zc.controller.admin.logistics.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 物流公司新增/修改 Request VO")
@Data
public class LogisticsSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "32535")
    private Long id;

    @Schema(description = "编码，例如：shunfeng", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "编码，例如：shunfeng不能为空")
    private String code;

    @Schema(description = "名称，例如：顺丰快递", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @NotEmpty(message = "名称，例如：顺丰快递不能为空")
    private String name;

    @Schema(description = "联系人", example = "王五")
    private String contactName;

    @Schema(description = "电话")
    private String mobile;

    @Schema(description = "地址")
    private String address;

    @Schema(description = "备注")
    private String note;

}