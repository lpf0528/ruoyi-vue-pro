package cn.iocoder.yudao.module.zc.controller.admin.customer.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 客户资料新增/修改 Request VO")
@Data
public class ZcCustomerSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "8647")
    private Long id;

    @Schema(description = "简称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @NotEmpty(message = "简称不能为空")
    private String shortName;

    @Schema(description = "全称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @NotEmpty(message = "全称不能为空")
    private String name;

    @Schema(description = "联系人", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @NotEmpty(message = "联系人不能为空")
    private String contactName;

    @Schema(description = "固定地址")
    private String address;

    @Schema(description = "送货地址")
    private String deliveryAddress;

    @Schema(description = "手机")
    private String mobile;

    @Schema(description = "联系电话")
    private String mobile2;

    @Schema(description = "物流", example = "429")
    private Long logisticId;

    @Schema(description = "关联品牌", example = "22168")
    private Long brandId;

    @Schema(description = "账户余额")
    private BigDecimal balance;

    @Schema(description = "备注")
    private String note;

}