package cn.iocoder.yudao.module.zc.dal.dataobject.productversion;

import lombok.*;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 产品版本规格 DO
 *
 * @author 01Coder
 */
@TableName("zc_product_version_spc")
@KeySequence("zc_product_version_spc_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZcProductVersionSpcDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 版本
     */
    private Long versionId;
    /**
     * 规格
     */
    private String spec;
    /**
     * 进货价
     */
    private BigDecimal inboundPrice;
    /**
     * 一级类销售价
     */
    private BigDecimal onePrice;
    /**
     * 备注
     */
    private String note;

}
