package cn.iocoder.yudao.module.zc.dal.mysql.product;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.product.ZcProductDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.zc.controller.admin.product.vo.*;

/**
 * 产品 Mapper
 *
 * @author 01Coder
 */
@Mapper
public interface ZcProductMapper extends BaseMapperX<ZcProductDO> {

    default PageResult<ZcProductDO> selectPage(ZcProductPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ZcProductDO>()
                .likeIfPresent(ZcProductDO::getName, reqVO.getName())
                .eqIfPresent(ZcProductDO::getVersionId, reqVO.getVersionId())
                .eqIfPresent(ZcProductDO::getSpecId, reqVO.getSpecId())
                .eqIfPresent(ZcProductDO::getSupplierId, reqVO.getSupplierId())
                .betweenIfPresent(ZcProductDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ZcProductDO::getId));
    }

}