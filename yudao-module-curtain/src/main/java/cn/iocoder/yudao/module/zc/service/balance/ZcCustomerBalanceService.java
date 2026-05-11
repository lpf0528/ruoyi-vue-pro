package cn.iocoder.yudao.module.zc.service.balance;

import java.math.BigDecimal;

/**
 * 客户余额变更唯一入口：先流水后余额，事务内完成。
 */
public interface ZcCustomerBalanceService {

    /**
     * @param changeAmount 正数增加余额，负数减少余额
     */
    void changeBalance(Long customerId, BigDecimal changeAmount, String bizType,
                       String refType, Long refId, String refNo, String remark);

}
