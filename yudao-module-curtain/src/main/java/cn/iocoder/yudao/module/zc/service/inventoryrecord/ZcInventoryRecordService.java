package cn.iocoder.yudao.module.zc.service.inventoryrecord;

import jakarta.validation.*;
import cn.iocoder.yudao.module.zc.controller.admin.inventoryrecord.vo.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 盘点记录 Service 接口
 *
 * @author 芋道源码
 */
public interface ZcInventoryRecordService {

    /**
     * 创建盘点记录，同时更新批次剩余数量、规格并追加盘点备注
     *
     * @param createReqVO 创建信息
     * @return 盘点记录 ID
     */
    Long createInventoryRecord(@Valid ZcInventoryRecordSaveReqVO createReqVO);

    /**
     * 获得盘点记录分页
     *
     * @param pageReqVO 分页查询
     * @return 盘点记录分页
     */
    PageResult<ZcInventoryRecordRespVO> getInventoryRecordPage(ZcInventoryRecordPageReqVO pageReqVO);

}