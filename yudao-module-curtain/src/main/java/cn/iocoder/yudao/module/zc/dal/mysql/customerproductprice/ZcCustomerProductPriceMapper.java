package cn.iocoder.yudao.module.zc.dal.mysql.customerproductprice;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.customerproductprice.ZcCustomerProductPriceDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.iocoder.yudao.module.zc.controller.admin.customerproductprice.vo.*;

/**
 * 客户产品销售授权价 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface ZcCustomerProductPriceMapper extends BaseMapperX<ZcCustomerProductPriceDO> {

    IPage<ZcCustomerProductPriceRespVO> selectPageWithVO(IPage<?> page, @Param("reqVO") ZcCustomerProductPricePageReqVO reqVO);

    default PageResult<ZcCustomerProductPriceRespVO> selectPage(ZcCustomerProductPricePageReqVO reqVO) {
        IPage<ZcCustomerProductPriceRespVO> result = selectPageWithVO(
                new Page<>(reqVO.getPageNo(), reqVO.getPageSize()), reqVO);
        return new PageResult<>(result.getRecords(), result.getTotal());
    }

}