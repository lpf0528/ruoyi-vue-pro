package cn.iocoder.yudao.module.zc.dal.dataobject.base;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;

@TableName("zc_customer_balance_log")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZcCustomerBalanceLogDO extends TenantBaseDO {

    @TableId
    private Long id;
    private Long customerId;
    private BigDecimal changeAmount;
    private BigDecimal balanceBefore;
    private BigDecimal balanceAfter;
    private String bizType;
    private String refType;
    private Long refId;
    private String refNo;
    private String remark;

}
