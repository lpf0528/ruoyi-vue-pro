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

    void insertOrUpdateBatch(@Param("list") List<ZcCustomerProductPriceDO> list);

    /**
     * 根据客户 ID 和产品 ID 查询唯一授权价记录
     *
     * @param customerId 客户 ID
     * @param productId  产品 ID
     * @return 授权价记录，不存在时返回 null
     */
    default ZcCustomerProductPriceDO selectByCustomerIdAndProductId(Long customerId, Long productId) {
        return selectOne(ZcCustomerProductPriceDO::getCustomerId, customerId,
                ZcCustomerProductPriceDO::getProductId, productId);
    }

    default PageResult<ZcCustomerProductPriceRespVO> selectPage(ZcCustomerProductPricePageReqVO reqVO) {
        IPage<ZcCustomerProductPriceRespVO> result = selectPageWithVO(
                new Page<>(reqVO.getPageNo(), reqVO.getPageSize()), reqVO);
        return new PageResult<>(result.getRecords(), result.getTotal());
    }

}