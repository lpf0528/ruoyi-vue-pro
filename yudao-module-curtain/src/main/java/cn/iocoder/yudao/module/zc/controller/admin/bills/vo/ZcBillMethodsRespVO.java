package cn.iocoder.yudao.module.zc.controller.admin.bills.vo;

import cn.idev.excel.annotation.*;
import cn.hutool.core.annotation.Alias;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 收款方式 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ZcBillMethodsRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "5831")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "：支付宝、微信、银行卡")
    @ExcelProperty("名称")
    private String name;

    @Schema(description = "卡号")
    @ExcelProperty("卡号")
    private String cardNo;

    @Schema(description = "备注")
    @ExcelProperty("备注")
    private String note;

    /** 分组：0=系统配置，1=手工配置；JSON 字段名为 group（Swagger 保留名规避） */
    @Alias("group")
    @JsonProperty("group")
    @Schema(description = "分组：0=系统配置，1=手工配置", requiredMode = Schema.RequiredMode.REQUIRED, example = "1", name = "group")
    @ExcelProperty("分组")
    private Integer configGroup;

    @Schema(description = "创建者")
    @ExcelProperty("创建者")
    private String creator;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}