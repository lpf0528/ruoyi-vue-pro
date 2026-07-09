package cn.iocoder.yudao.module.zc.dal.mysql.processnode;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.processnode.ZcUserProcessNodeDO;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

/**
 * 员工-工序节点绑定 Mapper
 *
 * @author 01Coder
 */
@Mapper
public interface ZcUserProcessNodeMapper extends BaseMapperX<ZcUserProcessNodeDO> {

    /**
     * 查询某员工的所有绑定关系
     */
    default List<ZcUserProcessNodeDO> selectListByUserId(Long userId) {
        return selectList(Wrappers.<ZcUserProcessNodeDO>lambdaQuery()
                .eq(ZcUserProcessNodeDO::getUserId, userId));
    }

    /**
     * 批量查询多个员工的绑定关系（用于分页列表展示，避免 N+1）
     */
    default List<ZcUserProcessNodeDO> selectListByUserIds(Collection<Long> userIds) {
        return selectList(Wrappers.<ZcUserProcessNodeDO>lambdaQuery()
                .in(ZcUserProcessNodeDO::getUserId, userIds));
    }

    /**
     * 删除某员工的全部绑定关系（覆盖式保存时先清空再插入）
     */
    default void deleteByUserId(Long userId) {
        delete(Wrappers.<ZcUserProcessNodeDO>lambdaQuery()
                .eq(ZcUserProcessNodeDO::getUserId, userId));
    }

}
