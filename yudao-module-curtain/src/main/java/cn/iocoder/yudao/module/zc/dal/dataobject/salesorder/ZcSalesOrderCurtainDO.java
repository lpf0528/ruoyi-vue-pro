package cn.iocoder.yudao.module.zc.dal.dataobject.salesorder;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import cn.iocoder.yudao.module.zc.enums.ZcSalesOrderStatusEnum;

/**
 * 成品订单-窗帘行 DO
 *
 * @author o1Coder
 */
@TableName("zc_sales_order_curtain")
@KeySequence("zc_sales_order_curtain_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZcSalesOrderCurtainDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 销售单
     */
    private Long orderId;
    /**
     * 款式
     */
    private Long curtainId;
    /**
     * 房间
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private String room;
    /**
     * 褶倍快照
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private BigDecimal pleatRatioValue;
    /**
     * 折扣率
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private BigDecimal discountRate;
    /**
     * 应收金额；数据库 NOT NULL DEFAULT 0，null 在 Service 层归一为 0
     */
    private BigDecimal amount;
    /**
     * 图片1
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private String image1;
    /**
     * 图片2
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private String image2;
    /**
     * 配件多选（JSON 数组字符串），整单更新时前端可传空列表将其置空，
     * 需要 ALWAYS 策略才能在 updateById 中将该字段置为 null
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private String mountings;
    /**
     * 备注
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private String note;
    /**
     * 褶距
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private BigDecimal pleatsDistance;

    /**
     * 窗帘行状态，冗余自订单主表，随确认/取消确认操作同步更新，参见 {@link ZcSalesOrderStatusEnum}
     */
    private String status;

    /**
     * 序号，创建/整单更新时按前端传入列表顺序自动从 1 开始赋值
     */
    @TableField("`index`")
    private Integer index;

    /** 打包时间，执行打包操作时记录 */
    private LocalDateTime packTime;

    /** 发货时间，执行发货操作时记录 */
    private LocalDateTime shipTime;

}