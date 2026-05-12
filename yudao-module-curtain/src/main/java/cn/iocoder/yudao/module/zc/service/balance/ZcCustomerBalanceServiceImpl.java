package cn.iocoder.yudao.module.zc.service.balance;

import cn.iocoder.yudao.module.zc.dal.dataobject.base.ZcCustomerBalanceLogDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.base.ZcCustomerDO;
import cn.iocoder.yudao.module.zc.dal.mysql.base.ZcCustomerBalanceLogMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.base.ZcCustomerMapper;
import cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service
@Validated
public class ZcCustomerBalanceServiceImpl implements ZcCustomerBalanceService {

    @Resource
    private ZcCustomerMapper customerMapper;
    @Resource
    private ZcCustomerBalanceLogMapper customerBalanceLogMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changeBalance(Long customerId, BigDecimal changeAmount, String bizType,
                              String refType, Long refId, String refNo, String remark) {
        ZcCustomerDO customer = customerMapper.selectById(customerId);
        if (customer == null) {
            throw exception(ErrorCodeConstants.CUSTOMER_NOT_EXISTS);
        }
        BigDecimal before = customer.getBalance() != null ? customer.getBalance() : BigDecimal.ZERO;
        BigDecimal after = before.add(changeAmount);

        ZcCustomerBalanceLogDO log = ZcCustomerBalanceLogDO.builder()
                .customerId(customerId)
                .changeAmount(changeAmount)
                .balanceBefore(before)
                .balanceAfter(after)
                .bizType(bizType)
                .refType(refType)
                .refId(refId)
                .refNo(refNo)
                .remark(remark != null ? remark : "")
                .build();
        customerBalanceLogMapper.insert(log);

        customerMapper.updateById(new ZcCustomerDO().setId(customerId).setBalance(after));
    }

}
