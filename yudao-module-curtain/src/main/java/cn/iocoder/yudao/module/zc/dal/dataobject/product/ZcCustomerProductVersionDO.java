package cn.iocoder.yudao.module.zc.dal.dataobject.product;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;

@TableName("zc_customer_version_sales_authorization")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZcCustomerProductVersionDO extends TenantBaseDO {

    @TableId
    private Long id;
    private Long customerId;
    private Long productVersionId;
    private BigDecimal authorizedPrice;

}
