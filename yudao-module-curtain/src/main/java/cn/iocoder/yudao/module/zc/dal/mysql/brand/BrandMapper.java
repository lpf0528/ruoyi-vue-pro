package cn.iocoder.yudao.module.zc.dal.mysql.brand;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.brand.BrandDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.zc.controller.admin.brand.vo.*;

/**
 * 品牌 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface BrandMapper extends BaseMapperX<BrandDO> {

    default PageResult<BrandDO> selectPage(BrandPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<BrandDO>()
                .likeIfPresent(BrandDO::getName, reqVO.getName())
                .eqIfPresent(BrandDO::getLogo, reqVO.getLogo())
                .eqIfPresent(BrandDO::getMobile, reqVO.getMobile())
                .eqIfPresent(BrandDO::getAddress, reqVO.getAddress())
                .eqIfPresent(BrandDO::getNote, reqVO.getNote())
                .betweenIfPresent(BrandDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(BrandDO::getId));
    }

}