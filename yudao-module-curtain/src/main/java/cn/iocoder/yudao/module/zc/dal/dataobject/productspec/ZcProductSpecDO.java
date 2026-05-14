package cn.iocoder.yudao.module.zc.dal.dataobject.productspec;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 产品规格 DO
 *
 * @author 芋道源码
 */
@TableName("zc_product_spec")
@KeySequence("zc_product_spec_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZcProductSpecDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 规格值
     */
    private String value;
    /**
     * 备注
     */
    private String note;


}