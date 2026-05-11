package cn.iocoder.yudao.module.zc.service.progress;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.zc.controller.admin.vo.progress.ZcOrderProgressAppendReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.progress.ZcOrderProgressLogPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.progress.ZcProductionQueueUpdateReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.progress.ZcSalesOrderProductionQueueDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.progress.ZcSalesOrderProgressLogDO;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

public interface ZcOrderProgressService {

    void appendLog(Long orderId, Long definitionId, String progressCode, String progressName,
                   String actionType, LocalDateTime bizTime, String remark);

    void appendLog(@Valid ZcOrderProgressAppendReqVO reqVO);

    PageResult<ZcSalesOrderProgressLogDO> getProgressLogPage(@Valid ZcOrderProgressLogPageReqVO pageReqVO);

    void initProductionQueue(Long orderId);

    List<ZcSalesOrderProductionQueueDO> listProductionQueue(Long orderId);

    void updateProductionQueue(@Valid ZcProductionQueueUpdateReqVO reqVO);

}
