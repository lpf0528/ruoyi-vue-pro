package cn.iocoder.yudao.module.zc.dal.dataobject.sale;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;

@TableName("zc_sales_order_curtain_structure")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZcSalesOrderCurtainStructureDO extends TenantBaseDO {

    @TableId
    private Long id;
    private Long orderId;
    private Long orderCurtainId;
    private Long structureId;
    private BigDecimal height;
    private BigDecimal width;
    private String leftCorner;
    private String rightCorner;
    private String pasteDirection;
    private Long installProcessId;
    private String openMethod;
    private String processType;
    private Boolean isShaping;
    private Integer pleatsNum;
    private BigDecimal pleatsDistance;
    private BigDecimal skirtHeight;
    private String note;

}
