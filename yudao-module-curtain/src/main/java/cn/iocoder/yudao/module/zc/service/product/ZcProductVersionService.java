package cn.iocoder.yudao.module.zc.service.product;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.zc.controller.admin.vo.product.ZcProductVersionPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.product.ZcProductVersionSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.product.ZcProductVersionDO;

import javax.validation.Valid;

public interface ZcProductVersionService {

    Long create(@Valid ZcProductVersionSaveReqVO reqVO);

    void update(@Valid ZcProductVersionSaveReqVO reqVO);

    void delete(Long id);

    ZcProductVersionDO get(Long id);

    PageResult<ZcProductVersionDO> getPage(ZcProductVersionPageReqVO pageReqVO);

}
