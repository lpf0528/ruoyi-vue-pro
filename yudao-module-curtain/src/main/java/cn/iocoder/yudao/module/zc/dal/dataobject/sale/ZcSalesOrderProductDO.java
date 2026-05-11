package cn.iocoder.yudao.module.zc.dal.dataobject.sale;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;

@TableName("zc_sales_order_product")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZcSalesOrderProductDO extends TenantBaseDO {

    @TableId
    private Long id;
    private Long orderId;
    private Long productId;
    private Long batchId;
    private BigDecimal quantity;
    private BigDecimal price;
    private BigDecimal amount;
    private BigDecimal discountRate;
    private String note;

}
