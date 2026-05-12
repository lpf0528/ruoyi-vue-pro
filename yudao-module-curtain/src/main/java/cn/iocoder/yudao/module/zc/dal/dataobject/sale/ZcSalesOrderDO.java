package cn.iocoder.yudao.module.zc.dal.dataobject.sale;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@TableName("zc_sales_order")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZcSalesOrderDO extends TenantBaseDO {

    @TableId
    private Long id;
    private String orderNo;
    private Long customerId;
    private String mobile;
    private Long brandId;
    private String category;
    private LocalDate orderDate;
    private Long logisticId;
    private String receiver;
    private String deliveryAddress;
    private BigDecimal freight;
    private String types;
    private BigDecimal amount;
    private BigDecimal amountReceived;
    private LocalDate deliveryDate;
    private String payStatus;
    private String confirmStatus;
    private LocalDateTime confirmTime;
    private Boolean isExpedited;
    private String note;

}
