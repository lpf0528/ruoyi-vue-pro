package cn.iocoder.yudao.module.zc.dal.mysql.curtainseries;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtainseries.CurtainSeriesDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.zc.controller.admin.curtainseries.vo.*;

/**
 * 窗帘系列 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface CurtainSeriesMapper extends BaseMapperX<CurtainSeriesDO> {

    default PageResult<CurtainSeriesDO> selectPage(CurtainSeriesPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CurtainSeriesDO>()
                .likeIfPresent(CurtainSeriesDO::getName, reqVO.getName())
                .eqIfPresent(CurtainSeriesDO::getCategory, reqVO.getCategory())
                .eqIfPresent(CurtainSeriesDO::getNote, reqVO.getNote())
                .betweenIfPresent(CurtainSeriesDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(CurtainSeriesDO::getId));
    }

}