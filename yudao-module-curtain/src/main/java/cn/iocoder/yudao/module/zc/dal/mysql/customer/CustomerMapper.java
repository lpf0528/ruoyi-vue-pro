package cn.iocoder.yudao.module.zc.dal.mysql.customer;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.customer.CustomerDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.zc.controller.admin.customer.vo.*;

/**
 * 客户资料 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface CustomerMapper extends BaseMapperX<CustomerDO> {

    default PageResult<CustomerDO> selectPage(CustomerPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CustomerDO>()
                .likeIfPresent(CustomerDO::getShortName, reqVO.getShortName())
                .likeIfPresent(CustomerDO::getName, reqVO.getName())
                .likeIfPresent(CustomerDO::getContactName, reqVO.getContactName())
                .eqIfPresent(CustomerDO::getAddress, reqVO.getAddress())
                .eqIfPresent(CustomerDO::getProvince, reqVO.getProvince())
                .eqIfPresent(CustomerDO::getCity, reqVO.getCity())
                .eqIfPresent(CustomerDO::getDistrict, reqVO.getDistrict())
                .eqIfPresent(CustomerDO::getDeliveryAddress, reqVO.getDeliveryAddress())
                .eqIfPresent(CustomerDO::getMobile, reqVO.getMobile())
                .eqIfPresent(CustomerDO::getMobile2, reqVO.getMobile2())
                .eqIfPresent(CustomerDO::getLogisticId, reqVO.getLogisticId())
                .eqIfPresent(CustomerDO::getBrandId, reqVO.getBrandId())
                .eqIfPresent(CustomerDO::getBalance, reqVO.getBalance())
                .eqIfPresent(CustomerDO::getNote, reqVO.getNote())
                .betweenIfPresent(CustomerDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(CustomerDO::getId));
    }

}