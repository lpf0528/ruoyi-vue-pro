package cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo;

import cn.iocoder.yudao.module.zc.framework.operatelog.core.ZcBrandParseFunction;
import cn.iocoder.yudao.module.zc.framework.operatelog.core.ZcCustomerParseFunction;
import cn.iocoder.yudao.module.zc.framework.operatelog.core.ZcLogisticsParseFunction;
import com.mzt.logapi.starter.annotation.DiffLogField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 管理后台 - 销售订单整单创建 Request VO
 *
 * <p>整单创建接口的请求体，包含订单主信息和三层嵌套明细（窗帘行→结构行→用料明细）。
 * 订单号由 Service 层自动生成（格式：ZC + 租户ID + yyyyMMdd + 5位累计序号），
 * 结算状态、订单状态、是否加急由 Service 层设置默认值，无需前端传入。</p>
 *
 * @author 01Coder
 */
@Schema(description = "管理后台 - 销售订单整单创建 Request VO")
@Data
public class ZcSalesOrderCreateReqVO {

    /** 客户 ID，必填 */
    @Schema(description = "客户", requiredMode = Schema.RequiredMode.REQUIRED, example = "29746")
    @DiffLogField(name = "客户", function = ZcCustomerParseFunction.NAME)
    @NotNull(message = "客户不能为空")
    private Long customerId;

    /** 客户手机号 */
    @Schema(description = "手机")
    @DiffLogField(name = "手机")
    private String mobile;

    /** 品牌 */
    @Schema(description = "品牌", example = "8302")
    @DiffLogField(name = "品牌", function = ZcBrandParseFunction.NAME)
    private Long brandId;

    /** 下单日期，必填 */
    @Schema(description = "下单日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @DiffLogField(name = "下单日期")
    @NotNull(message = "下单日期不能为空")
    private LocalDate orderDate;

    /** 物流 ID，可为空；与 logisticName 二选一或同时传（优先 ID） */
    @Schema(description = "物流 ID，可为空", example = "27080")
    @DiffLogField(name = "物流", function = ZcLogisticsParseFunction.NAME)
    private Long logisticId;

    /** 物流名称；logisticId 为空时按名称查找，不存在则自动创建 */
    @Schema(description = "物流名称；logisticId 为空时按名称查找，不存在则自动创建", example = "顺丰速运")
    @DiffLogField(name = "物流名称")
    private String logisticName;

    /** 收货人姓名 */
    @Schema(description = "收货人")
    @DiffLogField(name = "收货人")
    private String receiver;

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

    /** 总金额（含运费等），不传默认为 0 */
    @Schema(description = "总金额")
    @DiffLogField(name = "总金额")
    private BigDecimal totalAmount;

    /** 订单金额（优惠后实收） */
    @Schema(description = "订单金额")
    @DiffLogField(name = "订单金额")
    private BigDecimal amount;

    /** 四舍五入差额（记录金额四舍五入产生的浮亏/浮盈），不传默认为 0 */
    @Schema(description = "四舍五入")
    @DiffLogField(name = "四舍五入")
    private BigDecimal rounding;

    /** 交付日期 */
    @Schema(description = "交付日期")
    @DiffLogField(name = "交付日期")
    private LocalDate deliveryDate;

    /** 备注 */
    @Schema(description = "备注")
    @DiffLogField(name = "备注")
    private String note;

    /** 窗帘行列表（含嵌套结构行与用料明细），至少包含一个 */
    @Schema(description = "窗帘行列表")
    @NotEmpty(message = "至少包含一个窗帘明细")
    @Valid
    private List<ZcSalesOrderCurtainCreateVO> curtains;

}
