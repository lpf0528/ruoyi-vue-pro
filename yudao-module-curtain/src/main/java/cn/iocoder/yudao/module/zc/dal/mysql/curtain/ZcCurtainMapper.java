package cn.iocoder.yudao.module.zc.dal.mysql.curtain;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtain.ZcCurtainDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.zc.controller.admin.curtain.vo.*;

/**
 * 窗帘 Mapper
 *
 * @author 01Coder
 */
@Mapper
public interface ZcCurtainMapper extends BaseMapperX<ZcCurtainDO> {

    default PageResult<ZcCurtainDO> selectPage(ZcCurtainPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ZcCurtainDO>()
                .likeIfPresent(ZcCurtainDO::getName, reqVO.getName())
                .orderByDesc(ZcCurtainDO::getId));
    }

    default List<ZcCurtainDO> selectList(ZcCurtainListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<ZcCurtainDO>()
                .likeIfPresent(ZcCurtainDO::getName, reqVO.getName())
                .orderByDesc(ZcCurtainDO::getId));
    }

    default ZcCurtainDO selectByName(String name) {
        return selectOne(new LambdaQueryWrapperX<ZcCurtainDO>()
                .eq(ZcCurtainDO::getName, name)
                .last("LIMIT 1"));
    }

}