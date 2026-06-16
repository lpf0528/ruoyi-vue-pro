package cn.iocoder.yudao.module.zc.dal.dataobject.productversion;

import lombok.*;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 客户版本销售授权价 DO
 *
 * <p>记录特定客户对特定产品版本下某规格的专属授权销售价格，
 * 唯一约束为 (customer_id, version_id, spec)。</p>
 *
 * @author 01Coder
 */
@TableName("zc_customer_version_spec_price")
@KeySequence("zc_customer_version_spec_price_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZcCustomerVersionSpecPriceDO extends BaseDO {

    /** 主键 */
    @TableId
    private Long id;

    /** 客户编号 */
    private Long customerId;

    /** 产品版本编号 */
    private Long versionId;

    /** 规格名称 */
    private String spec;

    /** 授权销售价格 */
    private BigDecimal authorizedPrice;

}
