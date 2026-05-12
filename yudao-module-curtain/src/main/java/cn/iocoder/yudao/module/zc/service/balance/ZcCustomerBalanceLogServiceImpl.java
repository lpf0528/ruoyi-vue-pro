package cn.iocoder.yudao.module.zc.service.balance;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.base.ZcCustomerBalanceLogDO;
import cn.iocoder.yudao.module.zc.dal.mysql.base.ZcCustomerBalanceLogMapper;
import cn.iocoder.yudao.module.zc.controller.admin.vo.balance.ZcCustomerBalanceLogPageReqVO;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

@Service
@Validated
public class ZcCustomerBalanceLogServiceImpl implements ZcCustomerBalanceLogService {

    @Resource
    private ZcCustomerBalanceLogMapper customerBalanceLogMapper;

    @Override
    public PageResult<ZcCustomerBalanceLogDO> getBalanceLogPage(ZcCustomerBalanceLogPageReqVO pageReqVO) {
        return customerBalanceLogMapper.selectPage(pageReqVO, new LambdaQueryWrapperX<ZcCustomerBalanceLogDO>()
                .eqIfPresent(ZcCustomerBalanceLogDO::getCustomerId, pageReqVO.getCustomerId())
                .eqIfPresent(ZcCustomerBalanceLogDO::getBizType, pageReqVO.getBizType())
                .orderByDesc(ZcCustomerBalanceLogDO::getId));
    }

}
