package cn.iocoder.yudao.module.zc.dal.mysql.processnode;

import java.util.*;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.processnode.ZcProcessNodeElementDO;
import org.apache.ibatis.annotations.Mapper;

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
     * @param processNodeId 节点ID
     */
    default void deleteByProcessNodeId(Long processNodeId) {
        delete(new LambdaQueryWrapperX<ZcProcessNodeElementDO>()
                .eq(ZcProcessNodeElementDO::getProcessNodeId, processNodeId));
    }

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
