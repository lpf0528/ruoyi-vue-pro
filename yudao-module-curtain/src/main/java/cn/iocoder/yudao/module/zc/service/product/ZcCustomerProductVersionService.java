package cn.iocoder.yudao.module.zc.service.product;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.zc.controller.admin.vo.product.ZcCustomerProductVersionPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.product.ZcCustomerProductVersionSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.product.ZcCustomerProductVersionDO;

import javax.validation.Valid;

public interface ZcCustomerProductVersionService {

    Long create(@Valid ZcCustomerProductVersionSaveReqVO reqVO);

    void update(@Valid ZcCustomerProductVersionSaveReqVO reqVO);

    void delete(Long id);

    ZcCustomerProductVersionDO get(Long id);

    PageResult<ZcCustomerProductVersionDO> getPage(ZcCustomerProductVersionPageReqVO pageReqVO);

}
