package cn.iocoder.yudao.module.zc.service.progress;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.zc.controller.admin.vo.progress.ZcProgressDefinitionPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.progress.ZcProgressDefinitionSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.progress.ZcProgressDefinitionDO;

import javax.validation.Valid;

public interface ZcProgressDefinitionService {

    Long create(@Valid ZcProgressDefinitionSaveReqVO reqVO);

    void update(@Valid ZcProgressDefinitionSaveReqVO reqVO);

    void delete(Long id);

    ZcProgressDefinitionDO get(Long id);

    PageResult<ZcProgressDefinitionDO> getPage(ZcProgressDefinitionPageReqVO pageReqVO);

}
