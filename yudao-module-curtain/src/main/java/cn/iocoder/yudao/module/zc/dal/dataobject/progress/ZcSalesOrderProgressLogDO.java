package cn.iocoder.yudao.module.zc.dal.dataobject.progress;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

@TableName("zc_sales_order_progress_log")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZcSalesOrderProgressLogDO extends TenantBaseDO {

    @TableId
    private Long id;
    private Long orderId;
    private Long definitionId;
    private String progressCode;
    private String progressName;
    private String actionType;
    private Long operatorId;
    private String operatorName;
    private LocalDateTime bizTime;
    private String detailJson;
    private String remark;

}
