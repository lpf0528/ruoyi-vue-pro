package cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 管理后台 - 面单整单创建 Request VO
 *
 * <p>订单类型由 Service 层固定写入 FABRIC，无需前端传入。</p>
 *
 * @author 01Coder
 */
@Schema(description = "管理后台 - 面单整单创建 Request VO")
@Data
public class ZcSalesOrderFabricCreateReqVO {

    /** 客户 ID，必填 */
    @Schema(description = "客户", requiredMode = Schema.RequiredMode.REQUIRED, example = "29746")
    @NotNull(message = "客户不能为空")
    private Long customerId;

    /** 客户手机号 */
    @Schema(description = "手机")
    private String mobile;

    /** 品牌 */
    @Schema(description = "品牌", example = "8302")
    private Long brandId;

    /** 下单日期，必填 */
    @Schema(description = "下单日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "下单日期不能为空")
    private LocalDate orderDate;

    /** 物流 */
    @Schema(description = "物流", example = "27080")
    private Long logisticId;

    /** 收货人姓名 */
    @Schema(description = "收货人")
    private String receiver;

    /** 送货地址 */
    @Schema(description = "送货地址")
    private String deliveryAddress;

    /** 订单金额 */
    @Schema(description = "订单金额")
    private BigDecimal amount;

    /** 窗帘行列表 */
    @Schema(description = "窗帘行列表")
    @Valid
    private List<ZcSalesOrderFabricCurtainCreateVO> curtains;

}
