package cn.iocoder.yudao.module.zc.service.stock;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.zc.dal.dataobject.stock.ZcPurchaseOrderDO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.stock.ZcPurchaseOrderPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.stock.ZcPurchaseOrderSaveReqVO;

import javax.validation.Valid;

public interface ZcPurchaseOrderService {

    Long create(@Valid ZcPurchaseOrderSaveReqVO reqVO);

    void update(@Valid ZcPurchaseOrderSaveReqVO reqVO);

    void delete(Long id);

    void audit(Long id);

    ZcPurchaseOrderDO get(Long id);

    PageResult<ZcPurchaseOrderDO> getPage(ZcPurchaseOrderPageReqVO pageReqVO);

}
