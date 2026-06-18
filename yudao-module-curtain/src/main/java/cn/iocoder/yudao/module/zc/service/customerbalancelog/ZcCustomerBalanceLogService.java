package cn.iocoder.yudao.module.zc.service.customerbalancelog;

import cn.iocoder.yudao.module.zc.controller.admin.customerbalancelog.vo.ZcCustomerBalanceLogPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.customerbalancelog.vo.ZcCustomerBalanceLogRespVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.customerbalancelog.ZcCustomerBalanceLogDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 客户余额变动流水 Service 接口
 *
 * @author 01Coder
 */
public interface ZcCustomerBalanceLogService {

    /**
     * 创建余额变动流水记录
     *
     * <p>由其他 Service（如销售订单、收款单）在调整客户余额后调用，
     * 将变动前后余额、业务类型、关联单据等信息写入流水表。</p>
     *
     * @param log 流水数据对象，需提前填充 customerId、changeAmount、balanceBefore、
     *            balanceAfter、bizType、refType、refId、refNo 等字段
     */
    void createLog(ZcCustomerBalanceLogDO log);

    /**
     * 获得客户余额变动流水分页
     *
     * @param pageReqVO 分页查询
     * @return 客户余额变动流水分页（含客户简称）
     */
    PageResult<ZcCustomerBalanceLogRespVO> getCustomerBalanceLogPage(ZcCustomerBalanceLogPageReqVO pageReqVO);

    /**
     * 获得指定客户、订单下最新一条「订单确认扣减」余额流水
     *
     * @param customerId 客户主键
     * @param refId      关联销售订单主键（zc_sales_order.id）
     * @return 最新流水，不存在则返回 null
     */
    ZcCustomerBalanceLogDO getLatestOrderConfirmLog(Long customerId, Long refId);

}