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
public class CustomerRespVO {

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

    @Schema(description = "省份")
    @ExcelProperty("省份")
    private String province;

    @Schema(description = "市区")
    @ExcelProperty("市区")
    private String city;

    @Schema(description = "县区")
    @ExcelProperty("县区")
    private String district;

    @Schema(description = "送货地址", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("送货地址")
    private String deliveryAddress;

    @Schema(description = "手机", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("手机")
    private String mobile;

    @Schema(description = "联系电话")
    @ExcelProperty("联系电话")
    private String mobile2;

    @Schema(description = "默认物流 zc_logistics.id", example = "429")
    @ExcelProperty("默认物流 zc_logistics.id")
    private Long logisticId;

    @Schema(description = "关联品牌 zc_brand.id", example = "22168")
    @ExcelProperty("关联品牌 zc_brand.id")
    private Long brandId;

    @Schema(description = "当前账户余额（业务变更必须同步写入 zc_customer_balance_log）", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("当前账户余额（业务变更必须同步写入 zc_customer_balance_log）")
    private BigDecimal balance;

    @Schema(description = "备注")
    @ExcelProperty("备注")
    private String note;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}