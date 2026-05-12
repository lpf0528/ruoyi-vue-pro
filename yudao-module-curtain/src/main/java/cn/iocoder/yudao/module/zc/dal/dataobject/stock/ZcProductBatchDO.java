package cn.iocoder.yudao.module.zc.dal.dataobject.stock;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@TableName("zc_product_batch")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZcProductBatchDO extends TenantBaseDO {

    @TableId
    private Long id;
    private Long purchaseOrderId;
    private String batchNo;
    private LocalDate inboundDate;
    private Long productId;
    private BigDecimal inboundQuantity;
    private BigDecimal quantity;
    private Long warehouseId;
    private Long supplierId;
    private String note;

}
