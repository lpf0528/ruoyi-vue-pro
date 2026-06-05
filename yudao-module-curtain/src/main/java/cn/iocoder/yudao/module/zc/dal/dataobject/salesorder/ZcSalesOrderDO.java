package cn.iocoder.yudao.module.zc.dal.dataobject.salesorder;

import lombok.*;

import java.time.LocalDate;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

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
    private String mobile;
    /**
     * 品牌
     */
    private Long brandId;
    /**
     * 下单日期
     */
    private LocalDate orderDate;
    /**
     * 物流
     */
    private Long logisticId;
    /**
     * 收货人
     */
    private String receiver;
    /**
     * 送货地址
     */
    private String deliveryAddress;
    /**
     * 运费
     */
    private BigDecimal freight;
    /**
     * 订单类型
     *
     * 枚举 {@link TODO zc_order_type 对应的类}
     */
    private String types;
    /**
     * 优惠金额
     */
    private BigDecimal discountAmount;
    /**
     * 总金额
     */
    private BigDecimal totalAmount;
    /**
     * 订单金额
     */
    private BigDecimal amount;
    /**
     * 已收金额
     */
    private BigDecimal amountReceived;
    /**
     * 交付日期
     */
    private LocalDate deliveryDate;
    /**
     * 结算状态
     *
     * 枚举 {@link TODO zc_order_pay_status 对应的类}
     */
    private String payStatus;
    /**
     * 状态
     *
     * 枚举 {@link TODO zc_order_status 对应的类}
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
    private String note;


}