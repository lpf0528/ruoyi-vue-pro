package cn.iocoder.yudao.module.zc.service.productcategory;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.zc.controller.admin.productcategory.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.productcategory.ProductCategoryDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.zc.dal.mysql.productcategory.ProductCategoryMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;

/**
 * 产品类别 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Resource
    private ProductCategoryMapper productCategoryMapper;

    @Override
    public Long createProductCategory(ProductCategorySaveReqVO createReqVO) {
        // 插入
        ProductCategoryDO productCategory = BeanUtils.toBean(createReqVO, ProductCategoryDO.class);
        productCategoryMapper.insert(productCategory);

        // 返回
        return productCategory.getId();
    }

    @Override
    public void updateProductCategory(ProductCategorySaveReqVO updateReqVO) {
        // 校验存在
        validateProductCategoryExists(updateReqVO.getId());
        // 更新
        ProductCategoryDO updateObj = BeanUtils.toBean(updateReqVO, ProductCategoryDO.class);
        productCategoryMapper.updateById(updateObj);
    }

    @Override
    public void deleteProductCategory(Long id) {
        // 校验存在
        validateProductCategoryExists(id);
        // 删除
        productCategoryMapper.deleteById(id);
    }

    @Override
        public void deleteProductCategoryListByIds(List<Long> ids) {
        // 删除
        productCategoryMapper.deleteByIds(ids);
        }


    private void validateProductCategoryExists(Long id) {
        if (productCategoryMapper.selectById(id) == null) {
            throw exception(PRODUCT_CATEGORY_NOT_EXISTS);
        }
    }

    @Override
    public ProductCategoryDO getProductCategory(Long id) {
        return productCategoryMapper.selectById(id);
    }

    @Override
    public PageResult<ProductCategoryDO> getProductCategoryPage(ProductCategoryPageReqVO pageReqVO) {
        return productCategoryMapper.selectPage(pageReqVO);
    }

}