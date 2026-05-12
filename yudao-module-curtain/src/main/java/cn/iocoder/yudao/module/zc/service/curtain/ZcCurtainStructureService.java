package cn.iocoder.yudao.module.zc.service.curtain;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.zc.controller.admin.vo.curtain.ZcCurtainStructurePageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.curtain.ZcCurtainStructureSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtain.ZcCurtainStructureDO;

import javax.validation.Valid;

public interface ZcCurtainStructureService {

    Long create(@Valid ZcCurtainStructureSaveReqVO reqVO);

    void update(@Valid ZcCurtainStructureSaveReqVO reqVO);

    void delete(Long id);

    ZcCurtainStructureDO get(Long id);

    PageResult<ZcCurtainStructureDO> getPage(ZcCurtainStructurePageReqVO pageReqVO);

}
