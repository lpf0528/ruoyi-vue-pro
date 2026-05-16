package cn.iocoder.yudao.module.zc.dal.dataobject.product;

import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 产品 DO
 *
 * @author 01Coder
 */
@TableName("zc_product")
@KeySequence("zc_product_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZcProductDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 版本
     */
    private Long versionId;
    /**
     * 进货价
     */
    private BigDecimal inboundPrice;
    /**
     * 规格
     */
    private Long specId;
    /**
     * 一级销售价
     */
    private BigDecimal onePrice;
    /**
     * 供应商
     */
    private Long supplierId;
    /**
     * 备注
     */
    private String note;


}