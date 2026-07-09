package cn.iocoder.yudao.module.zc.controller.admin.logistics.vo;

import com.mzt.logapi.starter.annotation.DiffLogField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 物流公司新增/修改 Request VO")
@Data
public class ZcLogisticsSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "32535")
    private Long id;

    @Schema(description = "编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "shunfeng")
    @DiffLogField(name = "编码")
    @NotEmpty(message = "编码不能为空")
    private String code;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "顺丰快递")
    @DiffLogField(name = "名称")
    @NotEmpty(message = "名称不能为空")
    private String name;

    @Schema(description = "联系人", example = "王五")
    @DiffLogField(name = "联系人")
    private String contactName;

    @Schema(description = "电话")
    @DiffLogField(name = "电话")
    private String mobile;

    @Schema(description = "地址")
    @DiffLogField(name = "地址")
    private String address;

    @Schema(description = "备注")
    @DiffLogField(name = "备注")
    private String note;

}
