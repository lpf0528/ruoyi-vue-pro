package cn.iocoder.yudao.module.zc.dal.mysql.productcategory;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.productcategory.ZcProductCategoryDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.zc.controller.admin.productcategory.vo.*;

/**
 * 产品类别 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface ZcProductCategoryMapper extends BaseMapperX<ZcProductCategoryDO> {

    default PageResult<ZcProductCategoryDO> selectPage(ZcProductCategoryPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ZcProductCategoryDO>()
                .likeIfPresent(ZcProductCategoryDO::getValue, reqVO.getValue())
                .eqIfPresent(ZcProductCategoryDO::getNote, reqVO.getNote())
                .betweenIfPresent(ZcProductCategoryDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ZcProductCategoryDO::getId));
    }

    default List<ZcProductCategoryDO> selectList(ZcProductCategoryListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<ZcProductCategoryDO>()
                .eqIfPresent(ZcProductCategoryDO::getValue, reqVO.getValue())
                .orderByDesc(ZcProductCategoryDO::getId));
    }

    default ZcProductCategoryDO selectByValue(String value) {
        return selectOne(new LambdaQueryWrapperX<ZcProductCategoryDO>()
                .eq(ZcProductCategoryDO::getValue, value)
                .last("LIMIT 1"));
    }

}