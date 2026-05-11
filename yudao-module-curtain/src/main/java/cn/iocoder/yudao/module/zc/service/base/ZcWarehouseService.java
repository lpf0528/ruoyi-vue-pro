package cn.iocoder.yudao.module.zc.service.base;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.zc.controller.admin.vo.base.ZcWarehousePageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.base.ZcWarehouseSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.base.ZcWarehouseDO;

import javax.validation.Valid;

public interface ZcWarehouseService {

    Long createWarehouse(@Valid ZcWarehouseSaveReqVO createReqVO);

    void updateWarehouse(@Valid ZcWarehouseSaveReqVO updateReqVO);

    void deleteWarehouse(Long id);

    ZcWarehouseDO getWarehouse(Long id);

    PageResult<ZcWarehouseDO> getWarehousePage(ZcWarehousePageReqVO pageReqVO);

}
