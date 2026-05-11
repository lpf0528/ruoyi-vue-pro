package cn.iocoder.yudao.module.zc.dal.dataobject.base;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;

@TableName("zc_customer")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZcCustomerDO extends TenantBaseDO {

    @TableId
    private Long id;
    private String shortName;
    private String name;
    private String contactName;
    private String address;
    private String province;
    private String city;
    private String district;
    private String deliveryAddress;
    private String mobile;
    private String mobile2;
    private Long logisticId;
    private Long brandId;
    private BigDecimal balance;
    private String note;

}
