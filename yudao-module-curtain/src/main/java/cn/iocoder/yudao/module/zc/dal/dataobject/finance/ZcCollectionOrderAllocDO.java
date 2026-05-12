package cn.iocoder.yudao.module.zc.dal.dataobject.finance;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;

@TableName("zc_collection_order_alloc")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZcCollectionOrderAllocDO extends TenantBaseDO {

    @TableId
    private Long id;
    private Long collectionId;
    private Long orderId;
    private BigDecimal payAmount;

}
