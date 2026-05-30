package cn.iocoder.yudao.module.zc.controller.admin.customerbalancelog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import cn.idev.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 客户余额变动流水 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ZcCustomerBalanceLogRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "10914")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "客户", requiredMode = Schema.RequiredMode.REQUIRED, example = "7166")
    @ExcelProperty("客户")
    private Long customerId;

    @Schema(description = "客户简称")
    @ExcelProperty("客户简称")
    private String customerShortName;

    @Schema(description = "余额变动额", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("余额变动额")
    private BigDecimal changeAmount;

    @Schema(description = "变动前余额", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("变动前余额")
    private BigDecimal balanceBefore;

    @Schema(description = "变动后余额", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("变动后余额")
    private BigDecimal balanceAfter;

    @Schema(description = "业务类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "业务类型", converter = DictConvert.class)
    @DictFormat("zc_customer_balance_biz_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String bizType;

    @Schema(description = "关联单据类型", example = "1")
    @ExcelProperty("关联单据类型")
    private String refType;

    @Schema(description = "关联单据主键", example = "27872")
    @ExcelProperty("关联单据主键")
    private Long refId;

    @Schema(description = "关联单号快照")
    @ExcelProperty("关联单号快照")
    private String refNo;

    @Schema(description = "备注", example = "你猜")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}