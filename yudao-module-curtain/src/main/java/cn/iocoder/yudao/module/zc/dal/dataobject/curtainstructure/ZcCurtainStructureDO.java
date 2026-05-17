package cn.iocoder.yudao.module.zc.dal.dataobject.curtainstructure;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 窗帘结构 DO
 *
 * @author 01Coder
 */
@TableName(value = "zc_curtain_structure", autoResultMap = true)
@KeySequence("zc_curtain_structure_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZcCurtainStructureDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 结构名称
     */
    private String name;
    /**
     * 属性多选：长、宽、高、等
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> attributes;
    /**
     * 备注
     */
    private String note;


}