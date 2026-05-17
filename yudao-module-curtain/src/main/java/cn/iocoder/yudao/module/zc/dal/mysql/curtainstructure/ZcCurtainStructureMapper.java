package cn.iocoder.yudao.module.zc.dal.mysql.curtainstructure;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtainstructure.ZcCurtainStructureDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.zc.controller.admin.curtainstructure.vo.*;

/**
 * 窗帘结构 Mapper
 *
 * @author 01Coder
 */
@Mapper
public interface ZcCurtainStructureMapper extends BaseMapperX<ZcCurtainStructureDO> {

    default PageResult<ZcCurtainStructureDO> selectPage(ZcCurtainStructurePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ZcCurtainStructureDO>()
                .likeIfPresent(ZcCurtainStructureDO::getName, reqVO.getName())
                .orderByDesc(ZcCurtainStructureDO::getId));
    }

    default List<ZcCurtainStructureDO> selectList(ZcCurtainStructureListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<ZcCurtainStructureDO>()
                .orderByDesc(ZcCurtainStructureDO::getId));
    }

}