package cn.iocoder.yudao.module.zc.dal.dataobject.salesorder;

import lombok.*;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 成品订单-用料明细 DO
 *
 * @author 01Coder
 */
@TableName("zc_sales_order_material")
@KeySequence("zc_sales_order_material_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZCSalesOrderMaterialDO extends BaseDO {

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
     * 结构行
     */
    private Long orderStructureId;
    /**
     * 组件类型
     */
    private Long elementId;
    /**
     * 货号
     */
    private Long productId;
    /**
     * 批次
     */
    private Long batchId;
    /**
     * 规格
     */
    private String spec;
    /**
     * 单价
     */
    private BigDecimal price;
    /**
     * 用料
     */
    private BigDecimal quantity;
    /**
     * 单位
     */
    private String unitValue;
    /**
     * 折扣率
     */
    private BigDecimal discountRate;
    /**
     * 小计
     */
    private BigDecimal amount;
    /**
     * 备注
     */
    private String note;
    /**
     * 配料状态，参见 {@link cn.iocoder.yudao.module.zc.enums.ZcSalesOrderMaterialStatusEnum}
     * 取值：NOT_PEILIAO=未配料，HAVE_PEILIAO=已配料
     */
    private String status;
    /**
     * 裁剪数量
     */
    private BigDecimal cutQuantity;

}