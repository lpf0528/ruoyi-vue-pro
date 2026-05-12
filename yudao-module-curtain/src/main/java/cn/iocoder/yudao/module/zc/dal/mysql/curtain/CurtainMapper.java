package cn.iocoder.yudao.module.zc.dal.mysql.curtain;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtain.CurtainDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.zc.controller.admin.curtain.vo.*;

/**
 * 窗帘 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface CurtainMapper extends BaseMapperX<CurtainDO> {

    default PageResult<CurtainDO> selectPage(CurtainPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CurtainDO>()
                .likeIfPresent(CurtainDO::getName, reqVO.getName())
                .eqIfPresent(CurtainDO::getSeriesId, reqVO.getSeriesId())
                .eqIfPresent(CurtainDO::getPasteDirection, reqVO.getPasteDirection())
                .eqIfPresent(CurtainDO::getOpenMethod, reqVO.getOpenMethod())
                .eqIfPresent(CurtainDO::getInstallProcessId, reqVO.getInstallProcessId())
                .eqIfPresent(CurtainDO::getProcessType, reqVO.getProcessType())
                .eqIfPresent(CurtainDO::getPleatRatioValue, reqVO.getPleatRatioValue())
                .eqIfPresent(CurtainDO::getPleatsDistance, reqVO.getPleatsDistance())
                .eqIfPresent(CurtainDO::getNote, reqVO.getNote())
                .betweenIfPresent(CurtainDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(CurtainDO::getId));
    }

}