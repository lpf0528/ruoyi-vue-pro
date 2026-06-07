package cn.iocoder.yudao.module.zc.dal.dataobject.salesorder;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 销售订单-产品行 DO
 *
 * <p>记录产品类订单（面料单等）中每条产品批次的用量与金额明细，
 * 与 {@link ZcSalesOrderDO} 通过 orderId 关联。</p>
 *
 * @author 01Coder
 */
@TableName("zc_sales_order_product")
@KeySequence("zc_sales_order_product_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZcSalesOrderProductDO extends BaseDO {

    /** 主键 */
    @TableId
    private Long id;

    /** 所属销售订单 ID，关联 zc_sales_order.id */
    private Long orderId;

    /** 产品 ID，关联 zc_product.id */
    private Long productId;

    /** 产品批次 ID，关联 zc_product_batch.id */
    private Long batchId;

    /** 数量 */
    private BigDecimal quantity;

    /** 单价 */
    private BigDecimal price;

    /** 行小计金额（quantity × price × 折扣等，由前端传入） */
    private BigDecimal amount;

    /** 备注 */
    private String note;

    /**
     * 序号，创建/整单更新时按前端传入列表顺序自动从 1 开始赋值
     */
    @TableField("`index`")
    private Integer index;

    /**
     * 产品行状态；确认/取消确认时同步订单主表状态（参见 {@link cn.iocoder.yudao.module.zc.enums.ZcSalesOrderStatusEnum}），
     * 裁剪操作后改写为配料状态（参见 {@link cn.iocoder.yudao.module.zc.enums.ZcSalesOrderMaterialStatusEnum}）：
     * HAVE_PEILIAO = 已配料，NOT_PEILIAO = 撤销裁剪后重置
     */
    private String status;

    /**
     * 裁剪数量；裁剪后记录实际出库数量，撤销裁剪后置为 null
     */
    private java.math.BigDecimal cutQuantity;

    /**
     * 发货时间；发货后记录，撤销发货后置为 null；不为 null 时视为已发货
     */
    private LocalDateTime shipTime;

}
