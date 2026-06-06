package cn.iocoder.yudao.module.zc.dal.dataobject.productversion;

import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 产品版本 DO
 *
 * @author 01Coder
 */
@TableName("zc_product_version")
@KeySequence("zc_product_version_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZcProductVersionDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 版本名称
     */
    private String name;
    /**
     * 单位（来自 zc_product_unit 字典，用户可配置，非固定枚举）
     */
    private String unitValue;
    /**
     * 类别ID
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private Long categoryId;
    /**
     * 出货价类型
     *
     * 枚举 {@link ZcSellingPriceTypeEnum}，字典类型 {@code zc_selling_price_type}
     */
    private String sellingPriceType;
    /**
     * 进货价
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private BigDecimal inboundPrice;
    /**
     * 一级类销售价
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private BigDecimal onePrice;
    /**
     * 物料分类
     *
     * 枚举 {@link ZcProductClassifyEnum}，字典类型 {@code zc_product_classify}
     */
    private String classify;
    /**
     * 供应商
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private Long supplierId;
    /**
     * 备注
     */
    private String note;


}