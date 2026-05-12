package cn.iocoder.yudao.module.zc.service.finance;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.zc.controller.admin.vo.finance.ZcCollectionDetailRespVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.finance.ZcCollectionPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.finance.ZcCollectionSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.finance.ZcCollectionRecordDO;

import javax.validation.Valid;

public interface ZcCollectionRecordService {

    Long createCollection(@Valid ZcCollectionSaveReqVO createReqVO);

    ZcCollectionRecordDO getCollection(Long id);

    ZcCollectionDetailRespVO getCollectionDetail(Long id);

    PageResult<ZcCollectionRecordDO> getCollectionPage(ZcCollectionPageReqVO pageReqVO);

}
