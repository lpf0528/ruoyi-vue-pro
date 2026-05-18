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
     * @return 销售订单分页（含关联的客户名称、物流名称、创建人名称）
     */
    PageResult<ZcSalesOrderRespVO> getSalesOrderPage(ZcSalesOrderPageReqVO pageReqVO);

    /**
     * 获得销售订单全量明细（三层嵌套结构）
     *
     * <p>返回该订单下所有窗帘行，每行含若干结构行，每个结构行含若干用料明细，
     * 并冗余各关联表的名称字段，避免前端多次请求。</p>
     *
     * @param orderId 销售订单 ID
     * @return 窗帘行列表（含嵌套的结构行与用料明细）
     */
    List<ZcSalesOrderCurtainDetailRespVO> getSalesOrderDetail(Long orderId);

}