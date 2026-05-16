package cn.iocoder.yudao.module.zc.service.product;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.zc.controller.admin.product.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.product.ZcProductDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 产品 Service 接口
 *
 * @author 芋道源码
 */
public interface ZcProductService {

    /**
     * 创建产品
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProduct(@Valid ZcProductSaveReqVO createReqVO);

    /**
     * 更新产品
     *
     * @param updateReqVO 更新信息
     */
    void updateProduct(@Valid ZcProductSaveReqVO updateReqVO);

    /**
     * 删除产品
     *
     * @param id 编号
     */
    void deleteProduct(Long id);

    /**
    * 批量删除产品
    *
    * @param ids 编号
    */
    void deleteProductListByIds(List<Long> ids);

    /**
     * 获得产品
     *
     * @param id 编号
     * @return 产品
     */
    ZcProductDO getProduct(Long id);

    /**
     * 获得产品分页
     *
     * @param pageReqVO 分页查询
     * @return 产品分页
     */
    PageResult<ZcProductDO> getProductPage(ZcProductPageReqVO pageReqVO);

    List<ZcProductDO> getProductList(ZcProductListReqVO listReqVO);

}