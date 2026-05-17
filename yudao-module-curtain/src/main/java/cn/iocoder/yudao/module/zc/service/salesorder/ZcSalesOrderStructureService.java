package cn.iocoder.yudao.module.zc.service.salesorder;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZcSalesOrderStructureDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 成品订单-结构 Service 接口
 *
 * @author 01Coder
 */
public interface ZcSalesOrderStructureService {

    /**
     * 创建成品订单-结构
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSalesOrderStructure(@Valid ZcSalesOrderStructureSaveReqVO createReqVO);

    /**
     * 更新成品订单-结构
     *
     * @param updateReqVO 更新信息
     */
    void updateSalesOrderStructure(@Valid ZcSalesOrderStructureSaveReqVO updateReqVO);

    /**
     * 删除成品订单-结构
     *
     * @param id 编号
     */
    void deleteSalesOrderStructure(Long id);

    /**
    * 批量删除成品订单-结构
    *
    * @param ids 编号
    */
    void deleteSalesOrderStructureListByIds(List<Long> ids);

    /**
     * 获得成品订单-结构
     *
     * @param id 编号
     * @return 成品订单-结构
     */
    ZcSalesOrderStructureDO getSalesOrderStructure(Long id);

    /**
     * 获得成品订单-结构分页
     *
     * @param pageReqVO 分页查询
     * @return 成品订单-结构分页
     */
    PageResult<ZcSalesOrderStructureDO> getSalesOrderStructurePage(ZcSalesOrderStructurePageReqVO pageReqVO);

}