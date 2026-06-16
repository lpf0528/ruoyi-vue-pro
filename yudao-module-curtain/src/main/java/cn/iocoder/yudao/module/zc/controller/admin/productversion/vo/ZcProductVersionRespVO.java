package cn.iocoder.yudao.module.zc.controller.admin.productversion.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.List;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import cn.idev.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 产品版本 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ZcProductVersionRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "22024")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "版本名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @ExcelProperty("版本名称")
    private String name;

    @Schema(description = "单位")
    @ExcelProperty(value = "单位", converter = DictConvert.class)
    @DictFormat("zc_product_unit") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String unitValue;

    @Schema(description = "类别ID", example = "18979")
    @ExcelProperty("类别ID")
    private Long categoryId;

    @Schema(description = "分类", requiredMode = Schema.RequiredMode.REQUIRED, example = "0壁纸 1运费 2样册 3其他 4窗帘 5窗纱 6成品")
    @ExcelProperty(value = "分类", converter = DictConvert.class)
    @DictFormat("zc_product_classify") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String classify;

    @Schema(description = "供应商", example = "7521")
    @ExcelProperty("供应商")
    private Long supplierId;

    @Schema(description = "备注")
    @ExcelProperty("备注")
    private String note;

    @Schema(description = "规格信息列表")
    private List<ZcProductVersionSpcRespVO> specConfs;

    @Schema(description = "类别名称")
    @ExcelProperty("类别名称")
    private String categoryValue;

    @Schema(description = "供应商名称")
    @ExcelProperty("供应商名称")
    private String supplierName;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}