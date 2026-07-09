package cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 管理后台 - 产品类销售订单详情 Response VO
 *
 * <p>包含订单主信息及产品行列表，一次响应前端无需二次请求。</p>
 *
 * @author 01Coder
 */
@Schema(description = "管理后台 - 产品类销售订单详情 Response VO")
@Data
public class ZcSalesOrderProductDetailRespVO {

    /** 订单 ID */
    @Schema(description = "订单 ID", example = "1024")
    private Long id;

    /** 订单号 */
    @Schema(description = "订单号")
    private String orderNo;

    /** 客户 ID */
    @Schema(description = "客户 ID", example = "29746")
    private Long customerId;

    /** 客户名称（冗余） */
    @Schema(description = "客户名称")
    private String customerName;

    /** 手机 */
    @Schema(description = "手机")
    private String mobile;

    /** 品牌 ID */
    @Schema(description = "品牌 ID", example = "8302")
    private Long brandId;

    /** 下单日期 */
    @Schema(description = "下单日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate orderDate;

    /** 物流 ID */
    @Schema(description = "物流 ID", example = "27080")
    private Long logisticId;

    /** 物流名称（冗余） */
    @Schema(description = "物流名称")
    private String logisticName;

    /** 收货人 */
    @Schema(description = "收货人")
    private String receiver;

    /** 送货地址 */
    @Schema(description = "送货地址")
    private String deliveryAddress;

    /** 运费 */
    @Schema(description = "运费")
    private BigDecimal freight;

    /** 订单类型 */
    @Schema(description = "订单类型")
    private String types;

    /** 优惠金额 */
    @Schema(description = "优惠金额")
    private BigDecimal discountAmount;

    /** 总金额 */
    @Schema(description = "总金额")
    private BigDecimal totalAmount;

    /** 订单金额 */
    @Schema(description = "订单金额")
    private BigDecimal amount;

    /** 四舍五入（记录金额四舍五入产生的浮亏/浮盈） */
    @Schema(description = "四舍五入")
    private BigDecimal rounding;

    /** 已收金额 */
    @Schema(description = "已收金额")
    private BigDecimal amountReceived;

    /** 交付日期 */
    @Schema(description = "交付日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate deliveryDate;

    /** 结算状态（unpaid/partial/paid） */
    @Schema(description = "结算状态")
    private String payStatus;

    /** 订单状态（unconfirmed/pending/processing/completed/cancelled） */
    @Schema(description = "订单状态")
    private String status;

    /** 确认时间 */
    @Schema(description = "确认时间")
    private LocalDateTime confirmTime;

    /** 是否加急 */
    @Schema(description = "是否加急")
    private Boolean isExpedited;

    /** 备注 */
    @Schema(description = "备注")
    private String note;

    /** 创建时间 */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    /** 产品行列表（含产品名称、批次号等冗余字段），字段名与创建接口 batchs 保持一致 */
    @Schema(description = "产品行列表")
    private List<ZcSalesOrderProductLineRespVO> batchs;

}
