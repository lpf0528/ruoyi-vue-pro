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
