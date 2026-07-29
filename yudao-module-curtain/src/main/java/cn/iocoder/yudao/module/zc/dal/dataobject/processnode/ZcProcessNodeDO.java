package cn.iocoder.yudao.module.zc.dal.dataobject.processnode;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 工序节点配置 DO
 *
 * @author 01Coder
 */
@TableName("zc_process_node")
@KeySequence("zc_process_node_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZcProcessNodeDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 工序名称，如：备料、裁剪、缝制、定型、质检、包装
     */
    private String name;
    /**
     * 排序号，数字越小越靠前
     */
    private Integer sort;
    /**
     * 工序描述/操作说明
     */
    private String description;
    /**
     * 分组：0=系统配置，1=手工配置
     *
     * <p>group 为 MySQL 保留字，使用 @TableField 显式映射列名</p>
     */
    @TableField("`group`")
    private Integer group;

    /**
     * 关联组件编号列表（JOIN 查询冗余字段，非数据库列）
     */
    @TableField(exist = false)
    private List<Long> elementIds;
    /**
     * 关联组件名称列表（JOIN 查询冗余字段，非数据库列）
     */
    @TableField(exist = false)
    private List<String> elementNames;

}