package cn.iocoder.yudao.module.zc.dal.dataobject.progress;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

@TableName("zc_sales_order_production_queue")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZcSalesOrderProductionQueueDO extends TenantBaseDO {

    @TableId
    private Long id;
    private Long orderId;
    private Long definitionId;
    private Integer queueStatus;
    private Integer sequenceNo;
    private LocalDateTime startedTime;
    private LocalDateTime completedTime;
    private Long operatorId;
    private String remark;

}
