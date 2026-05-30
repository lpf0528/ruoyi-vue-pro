package cn.iocoder.yudao.module.zc.dal.mysql.customerbalancelog;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.customerbalancelog.ZcCustomerBalanceLogDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.zc.controller.admin.customerbalancelog.vo.*;

/**
 * 客户余额变动流水 Mapper
 *
 * @author 01Coder
 */
@Mapper
public interface ZcCustomerBalanceLogMapper extends BaseMapperX<ZcCustomerBalanceLogDO> {

    default PageResult<ZcCustomerBalanceLogDO> selectPage(ZcCustomerBalanceLogPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ZcCustomerBalanceLogDO>()
                .eqIfPresent(ZcCustomerBalanceLogDO::getCustomerId, reqVO.getCustomerId())
                .eqIfPresent(ZcCustomerBalanceLogDO::getChangeAmount, reqVO.getChangeAmount())
                .eqIfPresent(ZcCustomerBalanceLogDO::getBalanceBefore, reqVO.getBalanceBefore())
                .eqIfPresent(ZcCustomerBalanceLogDO::getBalanceAfter, reqVO.getBalanceAfter())
                .eqIfPresent(ZcCustomerBalanceLogDO::getBizType, reqVO.getBizType())
                .eqIfPresent(ZcCustomerBalanceLogDO::getRefType, reqVO.getRefType())
                .eqIfPresent(ZcCustomerBalanceLogDO::getRefId, reqVO.getRefId())
                .eqIfPresent(ZcCustomerBalanceLogDO::getRefNo, reqVO.getRefNo())
                .eqIfPresent(ZcCustomerBalanceLogDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(ZcCustomerBalanceLogDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ZcCustomerBalanceLogDO::getId));
    }

}