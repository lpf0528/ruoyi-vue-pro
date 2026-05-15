package cn.iocoder.yudao.module.zc.controller.admin.productversion.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
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

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "22953")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "版本名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @ExcelProperty("版本名称")
    private String name;

    @Schema(description = "单位（字典）")
    @ExcelProperty(value = "单位（字典）", converter = DictConvert.class)
    @DictFormat("zc_product_unit") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String unitValue;

    @Schema(description = "规格值")
    @ExcelProperty("规格值")
    private String specValue;

    @Schema(description = "规格ID")
    @ExcelProperty("规格ID")
    private String specID;

    @Schema(description = "产品品类")
    @ExcelProperty("产品品类")
    private String categoryValue;

    @Schema(description = "产品品类ID")
    @ExcelProperty("产品品类ID")
    private Long categoryId;

    @Schema(description = "出货价类型", example = "fixed_price:统一价、sku_price:型号价")
    @ExcelProperty(value = "出货价类型", converter = DictConvert.class)
    @DictFormat("zc_selling_price_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String sellingPriceType;

    @Schema(description = "进货价", example = "13750")
    @ExcelProperty("进货价")
    private BigDecimal inboundPrice;

    @Schema(description = "分类")
    @ExcelProperty(value = "分类", converter = DictConvert.class)
    @DictFormat("zc_product_classify") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer classify;

    @Schema(description = "供应商", example = "21214")
    @ExcelProperty("供应商")
    private Long supplierId;

    @Schema(description = "备注")
    @ExcelProperty("备注")
    private String note;

    @Schema(description = "创建者")
    @ExcelProperty("创建者")
    private String creator;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
