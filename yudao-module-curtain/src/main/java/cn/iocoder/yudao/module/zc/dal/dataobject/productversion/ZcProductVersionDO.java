package cn.iocoder.yudao.module.zc.dal.dataobject.productversion;

import lombok.*;
import java.math.BigDecimal;
import java.util.List;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import cn.iocoder.yudao.module.zc.enums.ZcProductClassifyEnum;

/**
 * 产品版本 DO
 *
 * @author 01Coder
 */
@TableName(value = "zc_product_version", autoResultMap = true)
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
     * 物料分类
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