package cn.iocoder.yudao.module.zc.controller.admin.productversion.vo;

import cn.iocoder.yudao.module.zc.framework.operatelog.core.ZcSupplierParseFunction;
import com.mzt.logapi.starter.annotation.DiffLogField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 产品版本新增/修改 Request VO")
@Data
public class ZcProductVersionSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "22024")
    private Long id;

    @Schema(description = "版本名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @DiffLogField(name = "版本名称")
    @NotEmpty(message = "版本名称不能为空")
    private String name;

    @Schema(description = "单位")
    @DiffLogField(name = "单位")
    private String unitValue;

    @Schema(description = "类别ID", example = "18979")
    @DiffLogField(name = "类别")
    private Long categoryId;

    @Schema(description = "分类", requiredMode = Schema.RequiredMode.REQUIRED, example = "0壁纸 1运费 2样册 3其他 4窗帘 5窗纱 6成品")
    @DiffLogField(name = "分类")
    @NotEmpty(message = "分类不能为空")
    private String classify;

    @Schema(description = "供应商", example = "7521")
    @DiffLogField(name = "供应商", function = ZcSupplierParseFunction.NAME)
    private Long supplierId;

    @Schema(description = "备注")
    @DiffLogField(name = "备注")
    private String note;

    @Schema(description = "规格信息列表", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "规格信息不能为空，至少包含一个规格")
    @Valid
    private List<ZcProductVersionSpcSaveReqVO> specConfs;

}
