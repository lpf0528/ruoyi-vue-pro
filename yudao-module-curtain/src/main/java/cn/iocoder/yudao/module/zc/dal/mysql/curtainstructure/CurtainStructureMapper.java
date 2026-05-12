package cn.iocoder.yudao.module.zc.dal.mysql.curtainstructure;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtainstructure.CurtainStructureDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.zc.controller.admin.curtainstructure.vo.*;

/**
 * 窗帘结构部位 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface CurtainStructureMapper extends BaseMapperX<CurtainStructureDO> {

    default PageResult<CurtainStructureDO> selectPage(CurtainStructurePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CurtainStructureDO>()
                .likeIfPresent(CurtainStructureDO::getName, reqVO.getName())
                .eqIfPresent(CurtainStructureDO::getType, reqVO.getType())
                .eqIfPresent(CurtainStructureDO::getNote, reqVO.getNote())
                .betweenIfPresent(CurtainStructureDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(CurtainStructureDO::getId));
    }

}