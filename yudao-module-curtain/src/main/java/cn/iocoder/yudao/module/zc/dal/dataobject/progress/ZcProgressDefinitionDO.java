package cn.iocoder.yudao.module.zc.dal.dataobject.progress;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

@TableName("zc_progress_definition")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZcProgressDefinitionDO extends TenantBaseDO {

    @TableId
    private Long id;
    private String code;
    private String name;
    private Integer progressKind;
    private String phaseGroup;
    @TableField("`sort`")
    private Integer sort;
    private Boolean isMilestone;
    private Boolean allowRepeat;
    private Integer status;
    private String remark;

}
