package cn.iocoder.yudao.module.zc.dal.dataobject.product;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;

@TableName("zc_customer_product_sales_authorization")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZcCustomerProductDO extends TenantBaseDO {

    @TableId
    private Long id;
    private Long customerId;
    private Long productId;
    private BigDecimal authorizedPrice;

}
