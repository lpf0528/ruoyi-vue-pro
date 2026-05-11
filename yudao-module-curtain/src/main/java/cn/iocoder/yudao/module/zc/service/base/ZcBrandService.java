package cn.iocoder.yudao.module.zc.service.base;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.zc.controller.admin.vo.base.ZcBrandPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.base.ZcBrandSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.base.ZcBrandDO;

import javax.validation.Valid;

public interface ZcBrandService {

    Long create(@Valid ZcBrandSaveReqVO reqVO);

    void update(@Valid ZcBrandSaveReqVO reqVO);

    void delete(Long id);

    ZcBrandDO get(Long id);

    PageResult<ZcBrandDO> getPage(ZcBrandPageReqVO pageReqVO);

}
