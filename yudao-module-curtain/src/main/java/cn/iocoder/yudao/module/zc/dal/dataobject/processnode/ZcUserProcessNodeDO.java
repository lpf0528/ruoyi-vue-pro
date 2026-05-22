package cn.iocoder.yudao.module.zc.dal.dataobject.processnode;

import lombok.*;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 员工-工序节点绑定 DO
 *
 * <p>记录某员工被授权可操作哪些工序节点，
 * 员工新增工序记录时，系统校验所选节点是否在其绑定列表内。</p>
 *
 * @author 01Coder
 */
@TableName("zc_user_process_node")
@KeySequence("zc_user_process_node_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZcUserProcessNodeDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 员工用户 ID，关联 system_users.id
     */
    private Long userId;
    /**
     * 工序节点 ID，关联 zc_process_node.id
     */
    private Long nodeId;

}
