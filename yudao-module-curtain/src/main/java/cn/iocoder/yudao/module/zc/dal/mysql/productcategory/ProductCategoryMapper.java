package cn.iocoder.yudao.module.zc.dal.mysql.productcategory;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.productcategory.ProductCategoryDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.zc.controller.admin.productcategory.vo.*;

/**
 * 产品类别 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface ProductCategoryMapper extends BaseMapperX<ProductCategoryDO> {

    default PageResult<ProductCategoryDO> selectPage(ProductCategoryPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ProductCategoryDO>()
                .eqIfPresent(ProductCategoryDO::getValue, reqVO.getValue())
                .eqIfPresent(ProductCategoryDO::getNote, reqVO.getNote())
                .betweenIfPresent(ProductCategoryDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ProductCategoryDO::getId));
    }

}