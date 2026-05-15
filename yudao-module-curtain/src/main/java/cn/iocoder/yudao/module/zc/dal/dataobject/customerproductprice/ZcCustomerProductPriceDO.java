package cn.iocoder.yudao.module.zc.dal.dataobject.customerproductprice;

import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 客户产品销售授权价 DO
 *
 * @author 芋道源码
 */
@TableName("zc_customer_product_price")
@KeySequence("zc_customer_product_price_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZcCustomerProductPriceDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 客户
     */
    private Long customerId;
    /**
     * 产品
     */
    private Long productId;
    /**
     * 授权价格
     */
    private BigDecimal authorizedPrice;


}