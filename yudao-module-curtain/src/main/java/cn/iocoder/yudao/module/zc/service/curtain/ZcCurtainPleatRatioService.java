package cn.iocoder.yudao.module.zc.service.curtain;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.zc.controller.admin.vo.curtain.ZcCurtainPleatRatioPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.curtain.ZcCurtainPleatRatioSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtain.ZcCurtainPleatRatioDO;

import javax.validation.Valid;

public interface ZcCurtainPleatRatioService {

    Long create(@Valid ZcCurtainPleatRatioSaveReqVO reqVO);

    void update(@Valid ZcCurtainPleatRatioSaveReqVO reqVO);

    void delete(Long id);

    ZcCurtainPleatRatioDO get(Long id);

    PageResult<ZcCurtainPleatRatioDO> getPage(ZcCurtainPleatRatioPageReqVO pageReqVO);

}
