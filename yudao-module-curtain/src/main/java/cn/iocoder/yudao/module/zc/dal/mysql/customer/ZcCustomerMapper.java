package cn.iocoder.yudao.module.zc.dal.mysql.customer;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.customer.ZcCustomerDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.zc.controller.admin.customer.vo.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * 客户资料 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface ZcCustomerMapper extends BaseMapperX<ZcCustomerDO> {

    IPage<ZcCustomerRespVO> selectPageWithVO(IPage<?> page, @Param("reqVO") ZcCustomerPageReqVO reqVO);

    default PageResult<ZcCustomerRespVO> selectPage(ZcCustomerPageReqVO reqVO) {
        IPage<ZcCustomerRespVO> result = selectPageWithVO(
                new Page<>(reqVO.getPageNo(), reqVO.getPageSize()), reqVO);
        return new PageResult<>(result.getRecords(), result.getTotal());
    }

    default List<ZcCustomerDO> selectList(ZcCustomerListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<ZcCustomerDO>()
                .likeIfPresent(ZcCustomerDO::getShortName, reqVO.getShortName())
                .orderByDesc(ZcCustomerDO::getId));
    }

    default ZcCustomerDO selectByShortName(String shortName) {
        return selectOne(new LambdaQueryWrapperX<ZcCustomerDO>()
                .eq(ZcCustomerDO::getShortName, shortName)
                .last("LIMIT 1"));
    }

}