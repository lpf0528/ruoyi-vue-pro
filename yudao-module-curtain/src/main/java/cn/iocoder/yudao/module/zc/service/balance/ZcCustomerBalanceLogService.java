package cn.iocoder.yudao.module.zc.service.balance;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.zc.dal.dataobject.base.ZcCustomerBalanceLogDO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.balance.ZcCustomerBalanceLogPageReqVO;

public interface ZcCustomerBalanceLogService {

    PageResult<ZcCustomerBalanceLogDO> getBalanceLogPage(ZcCustomerBalanceLogPageReqVO pageReqVO);

}
