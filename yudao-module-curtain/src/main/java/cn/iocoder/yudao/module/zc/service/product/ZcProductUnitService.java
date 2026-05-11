package cn.iocoder.yudao.module.zc.service.product;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.zc.controller.admin.vo.product.ZcProductUnitPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.product.ZcProductUnitSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.product.ZcProductUnitDO;

import javax.validation.Valid;

public interface ZcProductUnitService {

    Long create(@Valid ZcProductUnitSaveReqVO reqVO);

    void update(@Valid ZcProductUnitSaveReqVO reqVO);

    void delete(Long id);

    ZcProductUnitDO get(Long id);

    PageResult<ZcProductUnitDO> getPage(ZcProductUnitPageReqVO pageReqVO);

}
