package cn.iocoder.yudao.module.zc.service.base;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.zc.controller.admin.vo.base.ZcCustomerPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.base.ZcCustomerSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.base.ZcCustomerDO;

import javax.validation.Valid;

public interface ZcCustomerService {

    Long createCustomer(@Valid ZcCustomerSaveReqVO createReqVO);

    void updateCustomer(@Valid ZcCustomerSaveReqVO updateReqVO);

    void deleteCustomer(Long id);

    ZcCustomerDO getCustomer(Long id);

    PageResult<ZcCustomerDO> getCustomerPage(ZcCustomerPageReqVO pageReqVO);

}
