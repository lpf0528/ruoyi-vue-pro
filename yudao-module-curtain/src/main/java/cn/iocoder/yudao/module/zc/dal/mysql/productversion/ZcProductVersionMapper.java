package cn.iocoder.yudao.module.zc.dal.mysql.productversion;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.productversion.ZcProductVersionDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.zc.controller.admin.productversion.vo.*;

/**
 * 产品版本 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface ZcProductVersionMapper extends BaseMapperX<ZcProductVersionDO> {

    default PageResult<ZcProductVersionDO> selectPage(ZcProductVersionPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ZcProductVersionDO>()
                .likeIfPresent(ZcProductVersionDO::getName, reqVO.getName())
                .eqIfPresent(ZcProductVersionDO::getUnitValue, reqVO.getUnitValue())
                .eqIfPresent(ZcProductVersionDO::getSpecId, reqVO.getSpecId())
                .eqIfPresent(ZcProductVersionDO::getCategoryId, reqVO.getCategoryId())
                .eqIfPresent(ZcProductVersionDO::getSellingPriceType, reqVO.getSellingPriceType())
                .betweenIfPresent(ZcProductVersionDO::getInboundPrice, reqVO.getInboundPrice())
                .eqIfPresent(ZcProductVersionDO::getClassify, reqVO.getClassify())
                .eqIfPresent(ZcProductVersionDO::getSupplierId, reqVO.getSupplierId())
                .eqIfPresent(ZcProductVersionDO::getCreator, reqVO.getCreator())
                .betweenIfPresent(ZcProductVersionDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ZcProductVersionDO::getId));
    }

    default List<ZcProductVersionDO> selectList(ZcProductVersionListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<ZcProductVersionDO>()
                .orderByDesc(ZcProductVersionDO::getId));
    }

}