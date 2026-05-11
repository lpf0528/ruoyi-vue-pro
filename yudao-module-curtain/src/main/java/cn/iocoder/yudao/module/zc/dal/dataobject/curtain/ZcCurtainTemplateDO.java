package cn.iocoder.yudao.module.zc.dal.dataobject.curtain;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

@TableName("zc_curtain_template")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZcCurtainTemplateDO extends TenantBaseDO {

    @TableId
    private Long id;
    private Long curtainId;
    private Long structureId;
    private Long elementId;
    private String unitValue;

}
