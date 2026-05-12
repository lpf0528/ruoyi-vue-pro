package cn.iocoder.yudao.module.zc.dal.mysql.logistics;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.logistics.LogisticsDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.zc.controller.admin.logistics.vo.*;

/**
 * 物流公司 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface LogisticsMapper extends BaseMapperX<LogisticsDO> {

    default PageResult<LogisticsDO> selectPage(LogisticsPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<LogisticsDO>()
                .eqIfPresent(LogisticsDO::getCode, reqVO.getCode())
                .likeIfPresent(LogisticsDO::getName, reqVO.getName())
                .likeIfPresent(LogisticsDO::getContactName, reqVO.getContactName())
                .eqIfPresent(LogisticsDO::getMobile, reqVO.getMobile())
                .eqIfPresent(LogisticsDO::getAddress, reqVO.getAddress())
                .eqIfPresent(LogisticsDO::getNote, reqVO.getNote())
                .betweenIfPresent(LogisticsDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(LogisticsDO::getId));
    }

}