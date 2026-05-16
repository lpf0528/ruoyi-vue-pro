package cn.iocoder.yudao.module.zc.controller.admin.productversion.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 产品版本分页 Request VO")
@Data
public class ZcProductVersionPageReqVO extends PageParam {

    @Schema(description = "版本名称", example = "芋艿")
    private String name;

    @Schema(description = "单位")
    private String unitValue;

    @Schema(description = "类别ID", example = "18979")
    private Long categoryId;

    @Schema(description = "出货价类型", example = "fixed_price / sku_price")
    private String sellingPriceType;

    @Schema(description = "分类", example = "0壁纸 1运费 2样册 3其他 4窗帘 5窗纱 6成品")
    private Integer classify;

    @Schema(description = "供应商", example = "7521")
    private Long supplierId;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}