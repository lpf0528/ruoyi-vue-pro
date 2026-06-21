package cn.iocoder.yudao.module.zc.service.logistics;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.zc.controller.admin.logistics.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.logistics.ZcLogisticsDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZcSalesOrderDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 物流公司 Service 接口
 *
 * @author 芋道源码
 */
public interface ZcLogisticsService {

    /**
     * 创建物流公司
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createLogistics(@Valid ZcLogisticsSaveReqVO createReqVO);

    /**
     * 更新物流公司
     *
     * @param updateReqVO 更新信息
     */
    void updateLogistics(@Valid ZcLogisticsSaveReqVO updateReqVO);

    /**
     * 删除物流公司
     *
     * @param id 编号
     */
    void deleteLogistics(Long id);

    /**
    * 批量删除物流公司
    *
    * @param ids 编号
    */
    void deleteLogisticsListByIds(List<Long> ids);

    /**
     * 获得物流公司
     *
     * @param id 编号
     * @return 物流公司
     */
    ZcLogisticsDO getLogistics(Long id);

    /**
     * 获得物流公司列表
     *
     * @param listReqVO 列表查询
     * @return 物流公司列表
     */
    List<ZcLogisticsDO> getLogisticsList(ZcLogisticsListReqVO listReqVO);

    /**
     * 获得物流公司分页
     *
     * @param pageReqVO 分页查询
     * @return 物流公司分页
     */
    PageResult<ZcLogisticsDO> getLogisticsPage(ZcLogisticsPageReqVO pageReqVO);

    /**
     * 解析销售订单物流：logisticId 可为空；仅传 logisticName 时按名称查找，不存在则自动创建并回填 ID。
     *
     * @param salesOrder 订单 DO（入参含 logisticId/logisticName，出参回填解析结果）
     */
    void resolveLogisticsForOrder(ZcSalesOrderDO salesOrder);

}
