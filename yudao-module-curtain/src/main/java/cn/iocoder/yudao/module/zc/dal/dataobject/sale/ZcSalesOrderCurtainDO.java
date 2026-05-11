package cn.iocoder.yudao.module.zc.dal.dataobject.sale;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;

@TableName("zc_sales_order_curtain")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZcSalesOrderCurtainDO extends TenantBaseDO {

    @TableId
    private Long id;
    private Long orderId;
    private Long curtainId;
    private String room;
    private BigDecimal pleatRatioValue;
    private BigDecimal discountRate;
    private BigDecimal amount;
    private String image1;
    private String image2;
    private String mountings;
    private String note;

}
