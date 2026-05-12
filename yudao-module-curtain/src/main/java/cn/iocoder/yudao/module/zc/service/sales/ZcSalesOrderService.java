package cn.iocoder.yudao.module.zc.service.sales;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.zc.controller.admin.vo.sales.ZcSalesOrderPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.sales.ZcSalesOrderRespVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.sales.ZcSalesOrderSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.sale.ZcSalesOrderDO;

import javax.validation.Valid;

public interface ZcSalesOrderService {

    Long createSalesOrder(@Valid ZcSalesOrderSaveReqVO createReqVO);

    void updateSalesOrder(@Valid ZcSalesOrderSaveReqVO updateReqVO);

    void deleteSalesOrder(Long id);

    ZcSalesOrderRespVO getSalesOrder(Long id);

    PageResult<ZcSalesOrderDO> getSalesOrderPage(ZcSalesOrderPageReqVO pageReqVO);

    void confirmSalesOrder(Long id);

    void cancelConfirmSalesOrder(Long id);

}
