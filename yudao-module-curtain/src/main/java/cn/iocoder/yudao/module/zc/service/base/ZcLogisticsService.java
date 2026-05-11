package cn.iocoder.yudao.module.zc.service.base;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.zc.controller.admin.vo.base.ZcLogisticsPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.base.ZcLogisticsSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.base.ZcLogisticsDO;

import javax.validation.Valid;

public interface ZcLogisticsService {

    Long create(@Valid ZcLogisticsSaveReqVO reqVO);

    void update(@Valid ZcLogisticsSaveReqVO reqVO);

    void delete(Long id);

    ZcLogisticsDO get(Long id);

    PageResult<ZcLogisticsDO> getPage(ZcLogisticsPageReqVO pageReqVO);

}
