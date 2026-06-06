package cn.iocoder.yudao.module.zc.dal.dataobject.salesorder;

import lombok.*;
import java.math.BigDecimal;
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
    private String room;
    /**
     * 褶倍快照
     */
    private BigDecimal pleatRatioValue;
    /**
     * 折扣率
     */
    private BigDecimal discountRate;
    /**
     * 应收金额
     */
    private BigDecimal amount;
    /**
     * 图片1
     */
    private String image1;
    /**
     * 图片2
     */
    private String image2;
    /**
     * 配件多选
     */
    private String mountings;
    /**
     * 备注
     */
    private String note;
    /**
     * 褶距
     */
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


}