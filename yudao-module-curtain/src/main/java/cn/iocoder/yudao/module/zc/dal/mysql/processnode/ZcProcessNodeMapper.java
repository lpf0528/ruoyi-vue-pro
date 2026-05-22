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
                .betweenIfPresent(ZcProcessNodeDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ZcProcessNodeDO::getId));
    }

}