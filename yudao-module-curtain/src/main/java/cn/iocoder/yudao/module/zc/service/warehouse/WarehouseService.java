package cn.iocoder.yudao.module.zc.service.warehouse;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.zc.controller.admin.warehouse.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.warehouse.WarehouseDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 仓库 Service 接口
 *
 * @author 芋道源码
 */
public interface WarehouseService {

    /**
     * 创建仓库
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createWarehouse(@Valid WarehouseSaveReqVO createReqVO);

    /**
     * 更新仓库
     *
     * @param updateReqVO 更新信息
     */
    void updateWarehouse(@Valid WarehouseSaveReqVO updateReqVO);

    /**
     * 删除仓库
     *
     * @param id 编号
     */
    void deleteWarehouse(Long id);

    /**
    * 批量删除仓库
    *
    * @param ids 编号
    */
    void deleteWarehouseListByIds(List<Long> ids);

    /**
     * 获得仓库
     *
     * @param id 编号
     * @return 仓库
     */
    WarehouseDO getWarehouse(Long id);

    /**
     * 获得仓库分页
     *
     * @param pageReqVO 分页查询
     * @return 仓库分页
     */
    PageResult<WarehouseDO> getWarehousePage(WarehousePageReqVO pageReqVO);

}