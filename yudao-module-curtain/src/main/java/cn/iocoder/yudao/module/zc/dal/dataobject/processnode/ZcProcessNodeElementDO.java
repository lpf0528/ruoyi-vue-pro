package cn.iocoder.yudao.module.zc.dal.dataobject.processnode;

import lombok.*;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 工序节点-关联组件 DO
 *
 * @author 01Coder
 */
@TableName("zc_process_node_element")
@KeySequence("zc_process_node_element_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZcProcessNodeElementDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 节点ID
     */
    private Long processNodeId;
    /**
     * 关联组件
     */
    private Long elementId;

}
