package cn.iocoder.yudao.module.zc.service.base;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.zc.controller.admin.vo.base.ZcPaymentPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.base.ZcPaymentSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.base.ZcPaymentDO;

import javax.validation.Valid;

public interface ZcPaymentService {

    Long create(@Valid ZcPaymentSaveReqVO reqVO);

    void update(@Valid ZcPaymentSaveReqVO reqVO);

    void delete(Long id);

    ZcPaymentDO get(Long id);

    PageResult<ZcPaymentDO> getPage(ZcPaymentPageReqVO pageReqVO);

}
