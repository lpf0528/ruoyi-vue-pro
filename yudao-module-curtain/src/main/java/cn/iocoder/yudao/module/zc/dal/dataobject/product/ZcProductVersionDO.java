package cn.iocoder.yudao.module.zc.dal.dataobject.product;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;

@TableName("zc_product_version")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZcProductVersionDO extends TenantBaseDO {

    @TableId
    private Long id;
    private String name;
    private String unitValue;
    private String specValue;
    private String categoryValue;
    private String sellingPriceType;
    private BigDecimal inboundPrice;
    private Integer bizType;
    private Long supplierId;
    private String note;

}
