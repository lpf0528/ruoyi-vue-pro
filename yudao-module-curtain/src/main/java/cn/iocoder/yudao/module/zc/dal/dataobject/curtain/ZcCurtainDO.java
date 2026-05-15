package cn.iocoder.yudao.module.zc.dal.dataobject.curtain;

import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 窗帘 DO
 *
 * @author 01Coder
 */
@TableName("zc_curtain")
@KeySequence("zc_curtain_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZcCurtainDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 款式名称
     */
    private String name;
    /**
     * 默认褶倍
     */
    private BigDecimal pleatRatioValue;
    /**
     * 褶距
     */
    private BigDecimal pleatsDistance;
    /**
     * 备注
     */
    private String note;


}