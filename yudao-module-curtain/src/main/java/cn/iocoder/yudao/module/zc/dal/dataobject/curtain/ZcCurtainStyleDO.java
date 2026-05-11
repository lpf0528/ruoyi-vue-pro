package cn.iocoder.yudao.module.zc.dal.dataobject.curtain;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;

@TableName("zc_curtain")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZcCurtainStyleDO extends TenantBaseDO {

    @TableId
    private Long id;
    private String name;
    private Long seriesId;
    private String pasteDirection;
    private String openMethod;
    private Long installProcessId;
    private String processType;
    private BigDecimal pleatRatioValue;
    private BigDecimal pleatsDistance;
    private String note;

}
