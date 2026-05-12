package cn.iocoder.yudao.module.zc.dal.dataobject.curtaintemplate;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 窗帘模板 DO
 *
 * @author 芋道源码
 */
@TableName("zc_curtain_template")
@KeySequence("zc_curtain_template_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurtainTemplateDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 款式
     */
    private Long curtainId;
    /**
     * 结构
     */
    private Long structureId;
    /**
     * 配件
     */
    private Long elementId;
    /**
     * 单位
     */
    private Long unitId;


}