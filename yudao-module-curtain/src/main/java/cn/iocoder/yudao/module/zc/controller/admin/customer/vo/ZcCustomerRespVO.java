package cn.iocoder.yudao.module.zc.controller.admin.customer.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import cn.idev.excel.annotation.*;

@Schema(description = "管理后台 - 客户资料 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ZcCustomerRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "8647")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "简称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @ExcelProperty("简称")
    private String shortName;

    @Schema(description = "全称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @ExcelProperty("全称")
    private String name;

    @Schema(description = "联系人", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @ExcelProperty("联系人")
    private String contactName;

    @Schema(description = "固定地址")
    @ExcelProperty("固定地址")
    private String address;

    @Schema(description = "送货地址")
    @ExcelProperty("送货地址")
    private String deliveryAddress;

    @Schema(description = "手机")
    @ExcelProperty("手机")
    private String mobile;

    @Schema(description = "联系电话")
    @ExcelProperty("联系电话")
    private String mobile2;

    @Schema(description = "物流", example = "429")
    @ExcelProperty("物流")
    private Long logisticId;

    @Schema(description = "关联品牌", example = "22168")
    @ExcelProperty("关联品牌")
    private Long brandId;

    @Schema(description = "账户余额")
    @ExcelProperty("账户余额")
    private BigDecimal balance;

    @Schema(description = "备注")
    @ExcelProperty("备注")
    private String note;

    @Schema(description = "物流名称")
    @ExcelProperty("物流名称")
    private String logisticName;

    @Schema(description = "品牌名称")
    @ExcelProperty("品牌名称")
    private String brandName;

    @Schema(description = "创建者")
    @ExcelProperty("创建者")
    private String creator;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}