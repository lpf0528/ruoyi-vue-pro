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
 * @author 01Coder
 */
@Mapper
public interface ZcProductVersionMapper extends BaseMapperX<ZcProductVersionDO> {

    default PageResult<ZcProductVersionDO> selectPage(ZcProductVersionPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ZcProductVersionDO>()
                .likeIfPresent(ZcProductVersionDO::getName, reqVO.getName())
                .eqIfPresent(ZcProductVersionDO::getUnitValue, reqVO.getUnitValue())
                .eqIfPresent(ZcProductVersionDO::getCategoryId, reqVO.getCategoryId())
                .eqIfPresent(ZcProductVersionDO::getSellingPriceType, reqVO.getSellingPriceType())
                .eqIfPresent(ZcProductVersionDO::getClassify, reqVO.getClassify())
                .eqIfPresent(ZcProductVersionDO::getSupplierId, reqVO.getSupplierId())
                .betweenIfPresent(ZcProductVersionDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ZcProductVersionDO::getId));
    }

}