package cn.iocoder.yudao.module.zc.controller.admin.customer.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Schema(description = "管理后台 - 客户资料精简 Response VO")
@Data
@Accessors(chain = true)
public class ZcCustomerSimpleRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "简称")
    private String shortName;

    @Schema(description = "全称")
    private String name;

    @Schema(description = "联系人")
    private String contactName;

    @Schema(description = "固定地址")
    private String address;

    @Schema(description = "送货地址")
    private String deliveryAddress;

    @Schema(description = "手机")
    private String mobile;

    @Schema(description = "联系电话")
    private String mobile2;

    @Schema(description = "物流")
    private Long logisticId;

    @Schema(description = "关联品牌")
    private Long brandId;

    @Schema(description = "账户余额")
    private BigDecimal balance;

}