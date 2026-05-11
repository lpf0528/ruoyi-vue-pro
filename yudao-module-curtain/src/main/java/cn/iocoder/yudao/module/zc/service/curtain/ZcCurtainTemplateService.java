package cn.iocoder.yudao.module.zc.service.curtain;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.zc.controller.admin.vo.curtain.ZcCurtainTemplatePageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.curtain.ZcCurtainTemplateSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtain.ZcCurtainTemplateDO;

import javax.validation.Valid;

public interface ZcCurtainTemplateService {

    Long create(@Valid ZcCurtainTemplateSaveReqVO reqVO);

    void update(@Valid ZcCurtainTemplateSaveReqVO reqVO);

    void delete(Long id);

    ZcCurtainTemplateDO get(Long id);

    PageResult<ZcCurtainTemplateDO> getPage(ZcCurtainTemplatePageReqVO pageReqVO);

}
