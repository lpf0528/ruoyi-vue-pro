package cn.iocoder.yudao.module.zc.service.salesorder;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZcSalesOrderCurtainDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 成品订单-窗帘行 Service 接口
 *
 * @author o1Coder
 */
public interface ZcSalesOrderCurtainService {

    /**
     * 创建成品订单-窗帘行
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSalesOrderCurtain(@Valid ZcSalesOrderCurtainSaveReqVO createReqVO);

    /**
     * 更新成品订单-窗帘行
     *
     * @param updateReqVO 更新信息
     */
    void updateSalesOrderCurtain(@Valid ZcSalesOrderCurtainSaveReqVO updateReqVO);

    /**
     * 删除成品订单-窗帘行
     *
     * @param id 编号
     */
    void deleteSalesOrderCurtain(Long id);

    /**
    * 批量删除成品订单-窗帘行
    *
    * @param ids 编号
    */
    void deleteSalesOrderCurtainListByIds(List<Long> ids);

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