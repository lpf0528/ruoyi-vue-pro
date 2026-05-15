package cn.iocoder.yudao.module.zc.dal.mysql.customerproductprice;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.customerproductprice.ZcCustomerProductPriceDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.zc.controller.admin.customerproductprice.vo.*;

/**
 * 客户产品销售授权价 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface ZcCustomerProductPriceMapper extends BaseMapperX<ZcCustomerProductPriceDO> {

    default PageResult<ZcCustomerProductPriceDO> selectPage(ZcCustomerProductPricePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ZcCustomerProductPriceDO>()
                .eqIfPresent(ZcCustomerProductPriceDO::getCustomerId, reqVO.getCustomerId())
                .eqIfPresent(ZcCustomerProductPriceDO::getProductId, reqVO.getProductId())
                .orderByDesc(ZcCustomerProductPriceDO::getId));
    }

}