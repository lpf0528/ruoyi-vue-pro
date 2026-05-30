package cn.iocoder.yudao.module.zc.controller.admin.product.vo;

import cn.iocoder.yudao.module.zc.framework.operatelog.core.ZcSupplierParseFunction;
import com.mzt.logapi.starter.annotation.DiffLogField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 产品新增/修改 Request VO")
@Data
public class ZcProductSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "27909")
    private Long id;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @DiffLogField(name = "名称")
    @NotEmpty(message = "名称不能为空")
    private String name;

    @Schema(description = "版本", requiredMode = Schema.RequiredMode.REQUIRED, example = "6")
    @DiffLogField(name = "版本")
    @NotNull(message = "版本不能为空")
    private Long versionId;

    @Schema(description = "进货价", example = "14151")
    @DiffLogField(name = "进货价")
    @DecimalMin(value = "0", message = "进货价不能小于0")
    private BigDecimal inboundPrice;

    @Schema(description = "规格", example = "27939")
    @DiffLogField(name = "规格")
    private Long specId;

    @Schema(description = "一级销售价", example = "25120")
    @DiffLogField(name = "一级销售价")
    @DecimalMin(value = "0", message = "一级销售价不能小于0")
    private BigDecimal onePrice;

    @Schema(description = "供应商", example = "25473")
    @DiffLogField(name = "供应商", function = ZcSupplierParseFunction.NAME)
    private Long supplierId;

    @Schema(description = "备注")
    @DiffLogField(name = "备注")
    private String note;

}
