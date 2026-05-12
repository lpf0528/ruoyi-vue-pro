package cn.iocoder.yudao.module.zc.dal.dataobject.curtainseries;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 窗帘系列 DO
 *
 * @author 芋道源码
 */
@TableName("zc_curtain_series")
@KeySequence("zc_curtain_series_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurtainSeriesDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 系列名称
     */
    private String name;
    /**
     * 0窗帘 1软装 2罗马帘 3百叶帘
     *
     * 枚举 {@link TODO curtain_category 对应的类}
     */
    private Integer category;
    /**
     * 备注
     */
    private String note;


}