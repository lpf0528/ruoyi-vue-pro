package cn.iocoder.yudao.module.zc.service.product;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.zc.controller.admin.vo.product.ZcProductPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.product.ZcProductSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.product.ZcProductDO;

import javax.validation.Valid;

public interface ZcProductService {

    Long create(@Valid ZcProductSaveReqVO reqVO);

    void update(@Valid ZcProductSaveReqVO reqVO);

    void delete(Long id);

    ZcProductDO get(Long id);

    PageResult<ZcProductDO> getPage(ZcProductPageReqVO pageReqVO);

}
