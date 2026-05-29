package cn.iocoder.yudao.module.zc.dal.mysql.brand;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.brand.ZcBrandDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.zc.controller.admin.brand.vo.*;

/**
 * 品牌 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface ZcBrandMapper extends BaseMapperX<ZcBrandDO> {

    default PageResult<ZcBrandDO> selectPage(ZcBrandPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ZcBrandDO>()
                .likeIfPresent(ZcBrandDO::getName, reqVO.getName())
                .orderByDesc(ZcBrandDO::getId));
    }

    default List<ZcBrandDO> selectList(ZcBrandListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<ZcBrandDO>()
                .likeIfPresent(ZcBrandDO::getName, reqVO.getName())
                .orderByDesc(ZcBrandDO::getId));
    }

    default ZcBrandDO selectByName(String name) {
        return selectOne(new LambdaQueryWrapperX<ZcBrandDO>()
                .eq(ZcBrandDO::getName, name)
                .last("LIMIT 1"));
    }

}