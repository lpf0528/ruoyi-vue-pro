package cn.iocoder.yudao.module.zc.dal.dataobject.product;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

@TableName("zc_product_category")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZcProductCategoryDO extends TenantBaseDO {

    @TableId
    private Long id;
    private String value;
    private String note;

}
