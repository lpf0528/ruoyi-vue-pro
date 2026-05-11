package cn.iocoder.yudao.module.zc.service.curtain;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.zc.controller.admin.vo.curtain.ZcCurtainSeriesPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.curtain.ZcCurtainSeriesSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtain.ZcCurtainSeriesDO;

import javax.validation.Valid;

public interface ZcCurtainSeriesService {

    Long create(@Valid ZcCurtainSeriesSaveReqVO reqVO);

    void update(@Valid ZcCurtainSeriesSaveReqVO reqVO);

    void delete(Long id);

    ZcCurtainSeriesDO get(Long id);

    PageResult<ZcCurtainSeriesDO> getPage(ZcCurtainSeriesPageReqVO pageReqVO);

}
