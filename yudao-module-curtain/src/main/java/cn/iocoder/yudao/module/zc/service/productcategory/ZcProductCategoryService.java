package cn.iocoder.yudao.module.zc.service.productcategory;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.zc.controller.admin.productcategory.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.productcategory.ZcProductCategoryDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 产品类别 Service 接口
 *
 * @author 芋道源码
 */
public interface ZcProductCategoryService {

    /**
     * 创建产品类别
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProductCategory(@Valid ZcProductCategorySaveReqVO createReqVO);

    /**
     * 更新产品类别
     *
     * @param updateReqVO 更新信息
     */
    void updateProductCategory(@Valid ZcProductCategorySaveReqVO updateReqVO);

    /**
     * 删除产品类别
     *
     * @param id 编号
     */
    void deleteProductCategory(Long id);

    /**
    * 批量删除产品类别
    *
    * @param ids 编号
    */
    void deleteProductCategoryListByIds(List<Long> ids);

    /**
     * 获得产品类别
     *
     * @param id 编号
     * @return 产品类别
     */
    ZcProductCategoryDO getProductCategory(Long id);

    /**
     * 获得产品类别分页
     *
     * @param pageReqVO 分页查询
     * @return 产品类别分页
     */
    PageResult<ZcProductCategoryDO> getProductCategoryPage(ZcProductCategoryPageReqVO pageReqVO);

    /**
     * 获得产品类别列表
     *
     * @param listReqVO 查询条件
     * @return 产品类别列表
     */
    List<ZcProductCategoryDO> getProductCategoryList(ZcProductCategoryListReqVO listReqVO);

}