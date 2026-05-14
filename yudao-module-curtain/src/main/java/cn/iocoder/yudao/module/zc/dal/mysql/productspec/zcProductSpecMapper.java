package cn.iocoder.yudao.module.zc.dal.mysql.productspec;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.productspec.zcProductSpecDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.zc.controller.admin.productspec.vo.*;

/**
 * 产品规格 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface zcProductSpecMapper extends BaseMapperX<zcProductSpecDO> {

    default PageResult<zcProductSpecDO> selectPage(zcProductSpecPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<zcProductSpecDO>()
                .eqIfPresent(zcProductSpecDO::getValue, reqVO.getValue())
                .orderByDesc(zcProductSpecDO::getId));
    }

}