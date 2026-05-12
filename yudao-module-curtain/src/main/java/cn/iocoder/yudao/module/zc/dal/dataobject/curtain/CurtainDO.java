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
 * @author 芋道源码
 */
@TableName("zc_curtain")
@KeySequence("zc_curtain_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurtainDO extends BaseDO {

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
     * 系列
     */
    private Long seriesId;
    /**
     * 粘贴方向
     *
     * 枚举 {@link TODO paste_direction 对应的类}
     */
    private String pasteDirection;
    /**
     * 打开方式
     *
     * 枚举 {@link TODO open_method 对应的类}
     */
    private String openMethod;
    /**
     * 默认安装工艺
     */
    private Long installProcessId;
    /**
     * 加工类型
     *
     * 枚举 {@link TODO process_type 对应的类}
     */
    private String processType;
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