package cn.iocoder.yudao.module.zc.service.stock;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.zc.dal.dataobject.stock.ZcInventoryRecordDO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.stock.ZcInventoryRecordPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.stock.ZcInventoryRecordSaveReqVO;

import javax.validation.Valid;

public interface ZcInventoryRecordService {

    Long create(@Valid ZcInventoryRecordSaveReqVO reqVO);

    ZcInventoryRecordDO get(Long id);

    PageResult<ZcInventoryRecordDO> getPage(ZcInventoryRecordPageReqVO pageReqVO);

}
