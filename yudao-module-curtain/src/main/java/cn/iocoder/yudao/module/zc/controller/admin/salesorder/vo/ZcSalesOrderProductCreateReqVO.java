package cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo;

import cn.iocoder.yudao.module.zc.framework.operatelog.core.ZcBrandParseFunction;
import cn.iocoder.yudao.module.zc.framework.operatelog.core.ZcCustomerParseFunction;
import cn.iocoder.yudao.module.zc.framework.operatelog.core.ZcLogisticsParseFunction;
import com.mzt.logapi.starter.annotation.DiffLogField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 管理后台 - 产品类销售订单整单创建 Request VO
 *
 * <p>适用于面料单等直接采购产品批次的订单类型。
 * 订单主记录写入 zc_sales_order，产品行明细写入 zc_sales_order_product。
 * 订单号由 Service 层自动生成，结算状态、订单状态、是否加急由 Service 层设置默认值。</p>
 *
 * @author 01Coder
 */
@Schema(description = "管理后台 - 产品类销售订单整单创建 Request VO")
@Data
public class ZcSalesOrderProductCreateReqVO {

    /** 客户 ID，必填 */
    @Schema(description = "客户 ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @DiffLogField(name = "客户", function = ZcCustomerParseFunction.NAME)
    @NotNull(message = "客户不能为空")
    private Long customerId;

    /** 客户手机号 */
    @Schema(description = "手机")
    @DiffLogField(name = "手机")
    private String mobile;

    /** 品牌 ID */
    @Schema(description = "品牌 ID")
    @DiffLogField(name = "品牌", function = ZcBrandParseFunction.NAME)
    private Long brandId;

    /** 下单日期，必填 */
    @Schema(description = "下单日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @DiffLogField(name = "下单日期")
    @NotNull(message = "下单日期不能为空")
    private LocalDate orderDate;

    /** 物流 ID */
    @Schema(description = "物流 ID")
    @DiffLogField(name = "物流", function = ZcLogisticsParseFunction.NAME)
    private Long logisticId;

    /** 收货人姓名 */
    @Schema(description = "收货人")
    @DiffLogField(name = "收货人")
    private String receiver;

    /** 交付日期 */
    @Schema(description = "交付日期")
    @DiffLogField(name = "交付日期")
    private LocalDate deliveryDate;

    /** 送货地址，必填 */
    @Schema(description = "送货地址", requiredMode = Schema.RequiredMode.REQUIRED)
    @DiffLogField(name = "送货地址")
    @NotEmpty(message = "送货地址不能为空")
    private String deliveryAddress;

    /** 运费，不传默认为 0 */
    @Schema(description = "运费")
    @DiffLogField(name = "运费")
    private BigDecimal freight;

    /** 优惠金额 */
    @Schema(description = "优惠金额")
    @DiffLogField(name = "优惠金额")
    private BigDecimal discountAmount;

    /** 订单金额（优惠后实收） */
    @Schema(description = "订单金额")
    @DiffLogField(name = "订单金额")
    private BigDecimal amount;

    /** 四舍五入差额（记录金额四舍五入产生的浮亏/浮盈），不传默认为 0 */
    @Schema(description = "四舍五入")
    @DiffLogField(name = "四舍五入")
    private BigDecimal rounding;

    /** 备注 */
    @Schema(description = "备注")
    @DiffLogField(name = "备注")
    private String note;

    /** 产品批次行列表，至少包含一行 */
    @Schema(description = "产品批次行列表")
    @NotEmpty(message = "至少包含一条产品行")
    @Valid
    private List<ZcSalesOrderProductBatchCreateVO> batchs;

}
