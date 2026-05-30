package cn.iocoder.yudao.module.zc.service.customerbalancelog;

import cn.iocoder.yudao.module.zc.controller.admin.customerbalancelog.vo.ZcCustomerBalanceLogPageReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.customerbalancelog.ZcCustomerBalanceLogDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 客户余额变动流水 Service 接口
 *
 * @author 01Coder
 */
public interface ZcCustomerBalanceLogService {

    /**
     * 获得客户余额变动流水分页
     *
     * @param pageReqVO 分页查询
     * @return 客户余额变动流水分页
     */
    PageResult<ZcCustomerBalanceLogDO> getCustomerBalanceLogPage(ZcCustomerBalanceLogPageReqVO pageReqVO);

}