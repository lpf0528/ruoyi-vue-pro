package cn.iocoder.yudao.module.zc.service.salesorder;

import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZcSalesOrderCurtainDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 成品订单-窗帘行 Service 接口
 *
 * @author o1Coder
 */
public interface ZcSalesOrderCurtainService {

    /**
     * 打包窗帘行
     *
     * <p>将指定窗帘行状态更新为已打包（DABAO）。若订单当前状态不是部分发货或已发货，
     * 则检查该订单下所有窗帘行是否全部已打包：全部已打包时订单状态更新为已打包，
     * 否则更新为部分打包。</p>
     *
     * @param id 窗帘行 ID
     */
    void packCurtain(Long id);

    /**
     * 获得成品订单-窗帘行
     *
     * @param id 编号
     * @return 成品订单-窗帘行
     */
    ZcSalesOrderCurtainDO getSalesOrderCurtain(Long id);

    /**
     * 获得成品订单-窗帘行分页
     *
     * @param pageReqVO 分页查询
     * @return 成品订单-窗帘行分页
     */
    PageResult<ZcSalesOrderCurtainDO> getSalesOrderCurtainPage(ZcSalesOrderCurtainPageReqVO pageReqVO);

}
