package cn.iocoder.yudao.module.zc.service.curtain;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.zc.controller.admin.vo.curtain.ZcCurtainInstallProcessPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.curtain.ZcCurtainInstallProcessSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtain.ZcCurtainInstallProcessDO;

import javax.validation.Valid;

public interface ZcCurtainInstallProcessService {

    Long create(@Valid ZcCurtainInstallProcessSaveReqVO reqVO);

    void update(@Valid ZcCurtainInstallProcessSaveReqVO reqVO);

    void delete(Long id);

    ZcCurtainInstallProcessDO get(Long id);

    PageResult<ZcCurtainInstallProcessDO> getPage(ZcCurtainInstallProcessPageReqVO pageReqVO);

}
