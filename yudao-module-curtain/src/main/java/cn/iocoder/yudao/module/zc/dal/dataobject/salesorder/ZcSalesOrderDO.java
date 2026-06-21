package cn.iocoder.yudao.module.zc.dal.dataobject.salesorder;

import lombok.*;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import cn.iocoder.yudao.module.zc.enums.ZcOrderTypeEnum;
import cn.iocoder.yudao.module.zc.enums.ZcSalesOrderPayStatusEnum;
import cn.iocoder.yudao.module.zc.enums.ZcSalesOrderStatusEnum;

/**
 * 销售订单 DO
 *
 * @author 01Coder
 */
@TableName("zc_sales_order")
@KeySequence("zc_sales_order_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZcSalesOrderDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 客户
     */
    private Long customerId;
    /**
     * 手机
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private String mobile;
    /**
     * 品牌
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private Long brandId;
    /**
     * 下单日期
     */
    private LocalDate orderDate;
    /**
     * 物流
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private Long logisticId;
    /**
     * 物流名字（订单快照；创建/更新时可仅传名称，Service 自动关联或新建 zc_logistics）
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private String logisticName;
    /**
     * 收货人
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private String receiver;
    /**
     * 送货地址；数据库 NOT NULL，null 在 Service 层归一为空串
     */
    private String deliveryAddress;
    /**
     * 运费；整单更新时前端不传则保留原值，故不使用 ALWAYS 策略
     */
    private BigDecimal freight;
    /**
     * 订单类型
     * 枚举 {@link ZcOrderTypeEnum}，字典类型 {@code zc_order_type}
     */
    private String types;
    /**
     * 优惠金额；数据库 NOT NULL DEFAULT 0，null 在 Service 层归一为 0
     */
    private BigDecimal discountAmount;
    /**
     * 总金额；数据库 NOT NULL DEFAULT 0，null 在 Service 层归一为 0
     */
    private BigDecimal totalAmount;
    /**
     * 订单金额；数据库 NOT NULL DEFAULT 0，null 在 Service 层归一为 0
     */
    private BigDecimal amount;
    /**
     * 四舍五入差额（记录金额四舍五入产生的浮亏/浮盈）；数据库 DEFAULT 0.00，null 在 Service 层归一为 0
     */
    private BigDecimal rounding;
    /**
     * 已收金额
     */
    private BigDecimal amountReceived;
    /**
     * 交付日期
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private LocalDate deliveryDate;
    /**
     * 支付状态
     * 枚举 {@link ZcSalesOrderPayStatusEnum}，字典类型 {@code zc_order_pay_status}
     */
    private String payStatus;
    /**
     * 状态
     * 枚举 {@link ZcSalesOrderStatusEnum}，字典类型 {@code zc_order_status}
     */
    private String status;
    /**
     * 确认时间
     */
    private LocalDateTime confirmTime;
    /**
     * 是否加急
     */
    private Boolean isExpedited;
    /**
     * 当前所处工序名称（工序记录写入时同步更新，便于列表快速展示生产进度）
     */
    private String currentNodeName;
    /**
     * 套数（成品单 = curtains 数量，面料单 = batchs 数量）
     */
    private Integer sets;
    /**
     * 备注
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private String note;


}