package cn.iocoder.yudao.module.zc.service.product;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.zc.controller.admin.vo.product.ZcProductCategoryPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.product.ZcProductCategorySaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.product.ZcProductCategoryDO;

import javax.validation.Valid;

public interface ZcProductCategoryService {

    Long create(@Valid ZcProductCategorySaveReqVO reqVO);

    void update(@Valid ZcProductCategorySaveReqVO reqVO);

    void delete(Long id);

    ZcProductCategoryDO get(Long id);

    PageResult<ZcProductCategoryDO> getPage(ZcProductCategoryPageReqVO pageReqVO);

}
