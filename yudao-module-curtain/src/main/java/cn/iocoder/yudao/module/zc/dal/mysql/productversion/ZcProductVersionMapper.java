package cn.iocoder.yudao.module.zc.dal.mysql.productversion;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.productversion.ZcProductVersionDO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import cn.iocoder.yudao.module.zc.controller.admin.productversion.vo.*;

/**
 * 产品版本 Mapper
 *
 * @author 01Coder
 */
@Mapper
public interface ZcProductVersionMapper extends BaseMapperX<ZcProductVersionDO> {

    IPage<ZcProductVersionRespVO> selectPageWithVO(IPage<?> page, @Param("reqVO") ZcProductVersionPageReqVO reqVO);

    default PageResult<ZcProductVersionRespVO> selectPage(ZcProductVersionPageReqVO reqVO) {
        IPage<ZcProductVersionRespVO> result = selectPageWithVO(
                new Page<>(reqVO.getPageNo(), reqVO.getPageSize()), reqVO);
        return new PageResult<>(result.getRecords(), result.getTotal());
    }

    default List<ZcProductVersionDO> selectList(ZcProductVersionListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<ZcProductVersionDO>()
                .orderByDesc(ZcProductVersionDO::getId));
    }

}