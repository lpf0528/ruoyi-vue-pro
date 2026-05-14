package cn.iocoder.yudao.module.zc.dal.mysql.customer;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.customer.ZcCustomerDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.zc.controller.admin.customer.vo.*;

/**
 * 客户资料 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface ZcCustomerMapper extends BaseMapperX<ZcCustomerDO> {

    default PageResult<ZcCustomerDO> selectPage(ZcCustomerPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ZcCustomerDO>()
                .likeIfPresent(ZcCustomerDO::getShortName, reqVO.getShortName())
                .likeIfPresent(ZcCustomerDO::getName, reqVO.getName())
                .eqIfPresent(ZcCustomerDO::getLogisticId, reqVO.getLogisticId())
                .eqIfPresent(ZcCustomerDO::getBrandId, reqVO.getBrandId())
                .orderByDesc(ZcCustomerDO::getId));
    }

}