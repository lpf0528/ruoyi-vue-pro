package cn.iocoder.yudao.module.zc.controller.admin.customer.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 客户资料分页 Request VO")
@Data
public class CustomerPageReqVO extends PageParam {

    @Schema(description = "简称", example = "张三")
    private String shortName;

    @Schema(description = "全称", example = "王五")
    private String name;

    @Schema(description = "联系人", example = "王五")
    private String contactName;

    @Schema(description = "固定地址")
    private String address;

    @Schema(description = "省份")
    private String province;

    @Schema(description = "市区")
    private String city;

    @Schema(description = "县区")
    private String district;

    @Schema(description = "送货地址")
    private String deliveryAddress;

    @Schema(description = "手机")
    private String mobile;

    @Schema(description = "联系电话")
    private String mobile2;

    @Schema(description = "默认物流 zc_logistics.id", example = "429")
    private Long logisticId;

    @Schema(description = "关联品牌 zc_brand.id", example = "22168")
    private Long brandId;

    @Schema(description = "当前账户余额（业务变更必须同步写入 zc_customer_balance_log）")
    private BigDecimal balance;

    @Schema(description = "备注")
    private String note;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}