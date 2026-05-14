package cn.iocoder.yudao.module.zc.dal.mysql.logistics;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.logistics.ZcLogisticsDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.zc.controller.admin.logistics.vo.*;

/**
 * 物流公司 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface ZcLogisticsMapper extends BaseMapperX<ZcLogisticsDO> {

    default PageResult<ZcLogisticsDO> selectPage(ZcLogisticsPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ZcLogisticsDO>()
                .eqIfPresent(ZcLogisticsDO::getCode, reqVO.getCode())
                .likeIfPresent(ZcLogisticsDO::getName, reqVO.getName())
                .likeIfPresent(ZcLogisticsDO::getContactName, reqVO.getContactName())
                .orderByDesc(ZcLogisticsDO::getId));
    }

}