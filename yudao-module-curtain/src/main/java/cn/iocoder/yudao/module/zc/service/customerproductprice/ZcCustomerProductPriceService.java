package cn.iocoder.yudao.module.zc.service.customerproductprice;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.zc.controller.admin.customerproductprice.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.customerproductprice.ZcCustomerProductPriceDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 客户产品销售授权价 Service 接口
 *
 * @author 芋道源码
 */
public interface ZcCustomerProductPriceService {

    /**
     * 创建客户产品销售授权价
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCustomerProductPrice(@Valid ZcCustomerProductPriceSaveReqVO createReqVO);

    /**
     * 批量创建客户产品销售授权价
     *
     * @param createReqVOs 创建信息列表
     */
    void createCustomerProductPriceList(@Valid List<ZcCustomerProductPriceSaveReqVO> createReqVOs);

    /**
     * 更新客户产品销售授权价
     *
     * @param updateReqVO 更新信息
     */
    void updateCustomerProductPrice(@Valid ZcCustomerProductPriceSaveReqVO updateReqVO);

    /**
     * 删除客户产品销售授权价
     *
     * @param id 编号
     */
    void deleteCustomerProductPrice(Long id);

    /**
    * 批量删除客户产品销售授权价
    *
    * @param ids 编号
    */
    void deleteCustomerProductPriceListByIds(List<Long> ids);

    /**
     * 获得客户产品销售授权价
     *
     * @param id 编号
     * @return 客户产品销售授权价
     */
    ZcCustomerProductPriceDO getCustomerProductPrice(Long id);

    /**
     * 获得客户产品销售授权价分页
     *
     * @param pageReqVO 分页查询
     * @return 客户产品销售授权价分页
     */
    PageResult<ZcCustomerProductPriceRespVO> getCustomerProductPricePage(ZcCustomerProductPricePageReqVO pageReqVO);

}