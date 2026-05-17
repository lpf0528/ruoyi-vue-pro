package cn.iocoder.yudao.module.zc.service.salesorder;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZcSalesOrderDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 销售订单 Service 接口
 *
 * @author 01Coder
 */
public interface ZcSalesOrderService {

    /**
     * 创建销售订单
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSalesOrder(@Valid ZcSalesOrderSaveReqVO createReqVO);

    /**
     * 更新销售订单
     *
     * @param updateReqVO 更新信息
     */
    void updateSalesOrder(@Valid ZcSalesOrderSaveReqVO updateReqVO);

    /**
     * 删除销售订单
     *
     * @param id 编号
     */
    void deleteSalesOrder(Long id);

    /**
    * 批量删除销售订单
    *
    * @param ids 编号
    */
    void deleteSalesOrderListByIds(List<Long> ids);

    /**
     * 获得销售订单
     *
     * @param id 编号
     * @return 销售订单
     */
    ZcSalesOrderDO getSalesOrder(Long id);

    /**
     * 获得销售订单分页
     *
     * @param pageReqVO 分页查询
     * @return 销售订单分页
     */
    PageResult<ZcSalesOrderDO> getSalesOrderPage(ZcSalesOrderPageReqVO pageReqVO);

}