package cn.iocoder.yudao.module.zc.service.curtain;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.zc.controller.admin.vo.curtain.ZcCurtainStylePageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.curtain.ZcCurtainStyleSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtain.ZcCurtainStyleDO;

import javax.validation.Valid;

public interface ZcCurtainStyleService {

    Long create(@Valid ZcCurtainStyleSaveReqVO reqVO);

    void update(@Valid ZcCurtainStyleSaveReqVO reqVO);

    void delete(Long id);

    ZcCurtainStyleDO get(Long id);

    PageResult<ZcCurtainStyleDO> getPage(ZcCurtainStylePageReqVO pageReqVO);

}
