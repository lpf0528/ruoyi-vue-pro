package cn.iocoder.yudao.module.zc.dal.mysql.processnode;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.processnode.ZcProcessNodeDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.zc.controller.admin.processnode.vo.*;

/**
 * 工序节点配置 Mapper
 *
 * @author 01Coder
 */
@Mapper
public interface ZcProcessNodeMapper extends BaseMapperX<ZcProcessNodeDO> {

    default PageResult<ZcProcessNodeDO> selectPage(ZcProcessNodePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ZcProcessNodeDO>()
                .likeIfPresent(ZcProcessNodeDO::getName, reqVO.getName())
                .eqIfPresent(ZcProcessNodeDO::getGroup, reqVO.getGroup())
                .betweenIfPresent(ZcProcessNodeDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ZcProcessNodeDO::getId));
    }

    /**
     * 根据名称查询工序节点（用于唯一性校验）
     *
     * @param name 工序节点名称
     * @return 匹配的工序节点，不存在返回 null
     */
    default ZcProcessNodeDO selectByName(String name) {
        return selectOne(ZcProcessNodeDO::getName, name);
    }

    /**
     * 查询工序节点配置列表，按排序号升序排列
     *
     * @param reqVO 列表查询条件
     * @return 工序节点配置列表
     */
    default List<ZcProcessNodeDO> selectList(ZcProcessNodeListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<ZcProcessNodeDO>()
                .eqIfPresent(ZcProcessNodeDO::getGroup, reqVO.getGroup())
                .orderByAsc(ZcProcessNodeDO::getSort));
    }

}