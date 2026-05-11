package cn.iocoder.yudao.module.zc.dal.dataobject.product;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;

@TableName("zc_product")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZcProductDO extends TenantBaseDO {

    @TableId
    private Long id;
    private String name;
    private Long versionId;
    private BigDecimal inboundPrice;
    private BigDecimal aPrice;
    private BigDecimal bPrice;
    private Long supplierId;
    private Integer purchaseType;
    private String note;

}
