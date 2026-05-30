package cn.iocoder.yudao.module.zc.service.customerbalancelog;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import cn.iocoder.yudao.module.zc.controller.admin.customerbalancelog.vo.ZcCustomerBalanceLogPageReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.customerbalancelog.ZcCustomerBalanceLogDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.zc.dal.mysql.customerbalancelog.ZcCustomerBalanceLogMapper;

/**
 * 客户余额变动流水 Service 实现类
 *
 * @author 01Coder
 */
@Service
@Validated
public class ZcCustomerBalanceLogServiceImpl implements ZcCustomerBalanceLogService {

    @Resource
    private ZcCustomerBalanceLogMapper customerBalanceLogMapper;

    @Override
    public void createLog(ZcCustomerBalanceLogDO log) {
        customerBalanceLogMapper.insert(log);
    }

    @Override
    public PageResult<ZcCustomerBalanceLogDO> getCustomerBalanceLogPage(ZcCustomerBalanceLogPageReqVO pageReqVO) {
        return customerBalanceLogMapper.selectPage(pageReqVO);
    }

}