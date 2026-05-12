package cn.iocoder.yudao.module.zc.service.product;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.zc.controller.admin.vo.product.ZcProductSpecPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.product.ZcProductSpecSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.product.ZcProductSpecDO;

import javax.validation.Valid;

public interface ZcProductSpecService {

    Long create(@Valid ZcProductSpecSaveReqVO reqVO);

    void update(@Valid ZcProductSpecSaveReqVO reqVO);

    void delete(Long id);

    ZcProductSpecDO get(Long id);

    PageResult<ZcProductSpecDO> getPage(ZcProductSpecPageReqVO pageReqVO);

}
