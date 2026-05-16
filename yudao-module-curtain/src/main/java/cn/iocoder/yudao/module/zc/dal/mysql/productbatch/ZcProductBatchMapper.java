package cn.iocoder.yudao.module.zc.dal.mysql.productbatch;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.productbatch.ZcProductBatchDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.zc.controller.admin.productbatch.vo.*;

/**
 * 产品批次 Mapper
 *
 * @author 01Coder
 */
@Mapper
public interface ZcProductBatchMapper extends BaseMapperX<ZcProductBatchDO> {

    default PageResult<ZcProductBatchDO> selectPage(ZcProductBatchPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ZcProductBatchDO>()
                .eqIfPresent(ZcProductBatchDO::getBatchNo, reqVO.getBatchNo())
                .betweenIfPresent(ZcProductBatchDO::getInboundDate, reqVO.getInboundDate())
                .eqIfPresent(ZcProductBatchDO::getProductId, reqVO.getProductId())
                .eqIfPresent(ZcProductBatchDO::getWarehouseId, reqVO.getWarehouseId())
                .eqIfPresent(ZcProductBatchDO::getSupplierId, reqVO.getSupplierId())
                .betweenIfPresent(ZcProductBatchDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ZcProductBatchDO::getId));
    }

}