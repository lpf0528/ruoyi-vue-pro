package cn.iocoder.yudao.module.zc.dal.mysql.processnode;

import java.util.*;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.processnode.ZcProcessNodeElementDO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 工序节点-关联组件 Mapper
 *
 * @author 01Coder
 */
@Mapper
public interface ZcProcessNodeElementMapper extends BaseMapperX<ZcProcessNodeElementDO> {

    /**
     * 按节点ID硬删除关联组件
     *
     * <p>zc_process_node_element 无 deleted 逻辑删除列，且业务上每次更新都需要彻底清空旧配置，
     * 因此用原生 SQL 物理删除，绕过 MyBatis Plus 对 {@link ZcProcessNodeElementDO}（继承自 BaseDO，
     * 标注了 {@code @TableLogic}）默认将 delete 改写为逻辑删除 UPDATE 的行为。</p>
     *
     * @param processNodeId 节点ID
     */
    @Delete("DELETE FROM zc_process_node_element WHERE process_node_id = #{processNodeId}")
    void deleteByProcessNodeId(@Param("processNodeId") Long processNodeId);

    /**
     * 查询指定节点关联的组件列表
     *
     * @param processNodeId 节点ID
     * @return 关联组件列表
     */
    default List<ZcProcessNodeElementDO> selectListByProcessNodeId(Long processNodeId) {
        return selectList(new LambdaQueryWrapperX<ZcProcessNodeElementDO>()
                .eq(ZcProcessNodeElementDO::getProcessNodeId, processNodeId));
    }

    /**
     * 批量查询多个节点关联的组件列表，用于列表页避免 N+1
     *
     * @param processNodeIds 节点ID集合
     * @return 关联组件列表
     */
    default List<ZcProcessNodeElementDO> selectListByProcessNodeIds(Collection<Long> processNodeIds) {
        return selectList(new LambdaQueryWrapperX<ZcProcessNodeElementDO>()
                .in(ZcProcessNodeElementDO::getProcessNodeId, processNodeIds));
    }

}
