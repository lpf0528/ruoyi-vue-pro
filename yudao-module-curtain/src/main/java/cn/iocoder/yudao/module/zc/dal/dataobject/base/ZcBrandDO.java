package cn.iocoder.yudao.module.zc.dal.dataobject.base;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

@TableName("zc_brand")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZcBrandDO extends TenantBaseDO {

    @TableId
    private Long id;
    private String name;
    private String logo;
    private String mobile;
    private String address;
    private String note;

}
