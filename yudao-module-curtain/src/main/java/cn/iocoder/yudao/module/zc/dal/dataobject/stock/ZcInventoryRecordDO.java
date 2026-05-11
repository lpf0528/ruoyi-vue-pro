package cn.iocoder.yudao.module.zc.dal.dataobject.stock;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;

@TableName("zc_inventory_record")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZcInventoryRecordDO extends TenantBaseDO {

    @TableId
    private Long id;
    private Long productId;
    private Long batchId;
    private BigDecimal oldQuantity;
    private BigDecimal newQuantity;
    private String note;

}
