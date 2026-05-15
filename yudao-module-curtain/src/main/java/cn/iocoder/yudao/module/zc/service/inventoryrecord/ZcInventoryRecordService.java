package cn.iocoder.yudao.module.zc.service.inventoryrecord;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.zc.controller.admin.inventoryrecord.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.inventoryrecord.ZcInventoryRecordDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 盘点记录 Service 接口
 *
 * @author 芋道源码
 */
public interface ZcInventoryRecordService {

    /**
     * 创建盘点记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createInventoryRecord(@Valid ZcInventoryRecordSaveReqVO createReqVO);

    /**
     * 更新盘点记录
     *
     * @param updateReqVO 更新信息
     */
    void updateInventoryRecord(@Valid ZcInventoryRecordSaveReqVO updateReqVO);

    /**
     * 删除盘点记录
     *
     * @param id 编号
     */
    void deleteInventoryRecord(Long id);

    /**
    * 批量删除盘点记录
    *
    * @param ids 编号
    */
    void deleteInventoryRecordListByIds(List<Long> ids);

    /**
     * 获得盘点记录
     *
     * @param id 编号
     * @return 盘点记录
     */
    ZcInventoryRecordDO getInventoryRecord(Long id);

    /**
     * 获得盘点记录分页
     *
     * @param pageReqVO 分页查询
     * @return 盘点记录分页
     */
    PageResult<ZcInventoryRecordDO> getInventoryRecordPage(ZcInventoryRecordPageReqVO pageReqVO);

}