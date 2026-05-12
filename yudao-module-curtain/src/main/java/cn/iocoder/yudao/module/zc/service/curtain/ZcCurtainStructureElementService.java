package cn.iocoder.yudao.module.zc.service.curtain;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.zc.controller.admin.vo.curtain.ZcCurtainStructureElementPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.curtain.ZcCurtainStructureElementSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtain.ZcCurtainStructureElementDO;

import javax.validation.Valid;

public interface ZcCurtainStructureElementService {

    Long create(@Valid ZcCurtainStructureElementSaveReqVO reqVO);

    void update(@Valid ZcCurtainStructureElementSaveReqVO reqVO);

    void delete(Long id);

    ZcCurtainStructureElementDO get(Long id);

    PageResult<ZcCurtainStructureElementDO> getPage(ZcCurtainStructureElementPageReqVO pageReqVO);

}
