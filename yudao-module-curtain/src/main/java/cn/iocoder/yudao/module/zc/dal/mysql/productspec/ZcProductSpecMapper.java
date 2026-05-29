package cn.iocoder.yudao.module.zc.dal.mysql.productspec;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.productspec.ZcProductSpecDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.zc.controller.admin.productspec.vo.*;

/**
 * 产品规格 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface ZcProductSpecMapper extends BaseMapperX<ZcProductSpecDO> {

    default PageResult<ZcProductSpecDO> selectPage(ZcProductSpecPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ZcProductSpecDO>()
                .eqIfPresent(ZcProductSpecDO::getValue, reqVO.getValue())
                .orderByDesc(ZcProductSpecDO::getId));
    }

    default List<ZcProductSpecDO> selectList(ZcProductSpecListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<ZcProductSpecDO>()
                .eqIfPresent(ZcProductSpecDO::getValue, reqVO.getValue())
                .orderByDesc(ZcProductSpecDO::getId));
    }

    default ZcProductSpecDO selectByValue(String value) {
        return selectOne(new LambdaQueryWrapperX<ZcProductSpecDO>()
                .eq(ZcProductSpecDO::getValue, value)
                .last("LIMIT 1"));
    }

}