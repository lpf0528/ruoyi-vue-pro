package cn.iocoder.yudao.module.zc.dal.dataobject.curtainpleatratio;

import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 褶倍 DO
 *
 * @author 芋道源码
 */
@TableName("zc_curtain_pleat_ratio")
@KeySequence("zc_curtain_pleat_ratio_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurtainPleatRatioDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 褶倍
     */
    private BigDecimal value;
    /**
     * 排序
     */
    private Integer rank;


}