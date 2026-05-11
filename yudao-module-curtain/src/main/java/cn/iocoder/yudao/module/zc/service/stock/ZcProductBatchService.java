package cn.iocoder.yudao.module.zc.service.stock;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.zc.dal.dataobject.stock.ZcProductBatchDO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.stock.ZcProductBatchPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.stock.ZcProductBatchSaveReqVO;

import javax.validation.Valid;
import java.math.BigDecimal;

public interface ZcProductBatchService {

    Long createInbound(@Valid ZcProductBatchSaveReqVO reqVO);

    /**
     * 扣减批次剩余数量（出库、裁剪等）
     */
    void deductQuantity(Long batchId, BigDecimal quantity);

    ZcProductBatchDO get(Long id);

    PageResult<ZcProductBatchDO> getPage(ZcProductBatchPageReqVO pageReqVO);

}
