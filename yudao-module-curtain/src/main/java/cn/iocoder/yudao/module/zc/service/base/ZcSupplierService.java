package cn.iocoder.yudao.module.zc.service.base;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.zc.controller.admin.vo.base.ZcSupplierPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.base.ZcSupplierSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.base.ZcSupplierDO;

import javax.validation.Valid;

public interface ZcSupplierService {

    Long create(@Valid ZcSupplierSaveReqVO reqVO);

    void update(@Valid ZcSupplierSaveReqVO reqVO);

    void delete(Long id);

    ZcSupplierDO get(Long id);

    PageResult<ZcSupplierDO> getPage(ZcSupplierPageReqVO pageReqVO);

}
