package cn.iocoder.yudao.module.zc.service.product;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.zc.controller.admin.product.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.product.ZcProductDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 货号档案 Service 接口
 *
 * @author 芋道源码
 */
public interface ZcProductService {

    /**
     * 创建货号档案
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProduct(@Valid ZcProductSaveReqVO createReqVO);

    /**
     * 更新货号档案
     *
     * @param updateReqVO 更新信息
     */
    void updateProduct(@Valid ZcProductSaveReqVO updateReqVO);

    /**
     * 删除货号档案
     *
     * @param id 编号
     */
    void deleteProduct(Long id);

    /**
    * 批量删除货号档案
    *
    * @param ids 编号
    */
    void deleteProductListByIds(List<Long> ids);

    /**
     * 获得货号档案
     *
     * @param id 编号
     * @return 货号档案
     */
    ZcProductDO getProduct(Long id);

    /**
     * 获得货号档案分页
     *
     * @param pageReqVO 分页查询
     * @return 货号档案分页
     */
    PageResult<ZcProductDO> getProductPage(ZcProductPageReqVO pageReqVO);

    List<ZcProductDO> getProductList(ZcProductListReqVO listReqVO);

}