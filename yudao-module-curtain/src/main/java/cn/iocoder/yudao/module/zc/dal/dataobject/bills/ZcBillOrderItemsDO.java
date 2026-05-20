package cn.iocoder.yudao.module.zc.dal.dataobject.bills;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

import java.math.BigDecimal;

/**
 * 收款单 - 订单分摊明细 DO
 *
 * <p>记录本次收款按订单的分摊金额，并更新对应订单的已收金额与结算状态。</p>
 *
 * @author 01Coder
 */
@TableName("zc_bill_order_items")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZcBillOrderItemsDO extends BaseDO {

    /** 主键 */
    @TableId
    private Long id;

    /** 关联收款单 ID */
    private Long billId;

    /** 关联销售订单 ID */
    private Long orderId;

    /** 本次分摊金额 */
    private BigDecimal allocatedAmount;

    /** 备注 */
    private String note;

}
