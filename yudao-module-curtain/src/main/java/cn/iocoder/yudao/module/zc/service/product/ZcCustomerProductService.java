package cn.iocoder.yudao.module.zc.service.product;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.zc.controller.admin.vo.product.ZcCustomerProductPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.product.ZcCustomerProductSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.product.ZcCustomerProductDO;

import javax.validation.Valid;

public interface ZcCustomerProductService {

    Long create(@Valid ZcCustomerProductSaveReqVO reqVO);

    void update(@Valid ZcCustomerProductSaveReqVO reqVO);

    void delete(Long id);

    ZcCustomerProductDO get(Long id);

    PageResult<ZcCustomerProductDO> getPage(ZcCustomerProductPageReqVO pageReqVO);

}
