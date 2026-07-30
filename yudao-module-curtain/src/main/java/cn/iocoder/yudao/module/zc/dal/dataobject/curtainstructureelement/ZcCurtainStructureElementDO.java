package cn.iocoder.yudao.module.zc.dal.dataobject.curtainstructureelement;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 窗帘结构组件 DO
 *
 * @author 01Coder
 */
@TableName("zc_curtain_structure_element")
@KeySequence("zc_curtain_structure_element_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZcCurtainStructureElementDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 组件名称
     */
    private String name;
    /**
     * 备注
     */
    private String note;
    /**
     * 版本
     */
    private Long versionId;
    /**
     * 是否打印，true=是，false=否，默认为 true
     */
    @TableField("is_print")
    private Boolean isPrint;
    /**
     * 是否计算用料，true=是，false=否，默认为 false
     */
    @TableField("is_cal_material")
    private Boolean isCalMaterial;


}