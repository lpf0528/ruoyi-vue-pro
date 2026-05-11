package cn.iocoder.yudao.module.zc.dal.dataobject.stock;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDate;

@TableName("zc_purchase_order")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZcPurchaseOrderDO extends TenantBaseDO {

    @TableId
    private Long id;
    private String purchaseNo;
    private LocalDate inboundDate;
    private Long supplierId;
    private String inboundType;
    private Long operatorId;
    private String poNo;
    private Integer auditStatus;
    private java.time.LocalDateTime auditTime;
    private Long auditorId;
    private String note;

}
