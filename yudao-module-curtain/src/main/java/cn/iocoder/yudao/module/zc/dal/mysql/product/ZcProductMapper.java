package cn.iocoder.yudao.module.zc.dal.mysql.product;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.product.ZcProductDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.iocoder.yudao.module.zc.controller.admin.product.vo.*;

/**
 * 产品 Mapper
 *
 * @author 01Coder
 */
@Mapper
public interface ZcProductMapper extends BaseMapperX<ZcProductDO> {

    IPage<ZcProductRespVO> selectPageWithVO(IPage<?> page, @Param("reqVO") ZcProductPageReqVO reqVO);

    default PageResult<ZcProductRespVO> selectPage(ZcProductPageReqVO reqVO) {
        IPage<ZcProductRespVO> result = selectPageWithVO(
                new Page<>(reqVO.getPageNo(), reqVO.getPageSize()), reqVO);
        return new PageResult<>(result.getRecords(), result.getTotal());
    }

    default List<ZcProductDO> selectList(ZcProductListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<ZcProductDO>()
                .likeIfPresent(ZcProductDO::getName, reqVO.getName())
                .eqIfPresent(ZcProductDO::getVersionId, reqVO.getVersionId())
                .orderByDesc(ZcProductDO::getId));
    }

    /** 统计指定版本下的产品数量 */
    default long countByVersionId(Long versionId) {
        return selectCount(new LambdaQueryWrapperX<ZcProductDO>()
                .eq(ZcProductDO::getVersionId, versionId));
    }

    default ZcProductDO selectByName(String name) {
        return selectOne(new LambdaQueryWrapperX<ZcProductDO>()
                .eq(ZcProductDO::getName, name)
                .last("LIMIT 1"));
    }

}