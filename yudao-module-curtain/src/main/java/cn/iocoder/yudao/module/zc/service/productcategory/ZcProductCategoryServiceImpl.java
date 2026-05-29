package cn.iocoder.yudao.module.zc.service.productcategory;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.zc.controller.admin.productcategory.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.productcategory.ZcProductCategoryDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.zc.dal.mysql.productcategory.ZcProductCategoryMapper;

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
public class ZcProductCategoryServiceImpl implements ZcProductCategoryService {

    @Resource
    private ZcProductCategoryMapper productCategoryMapper;

    @Override
    public Long createProductCategory(ZcProductCategorySaveReqVO createReqVO) {
        // 校验名称唯一性
        validateProductCategoryValueUnique(null, createReqVO.getValue());
        // 插入
        ZcProductCategoryDO productCategory = BeanUtils.toBean(createReqVO, ZcProductCategoryDO.class);
        productCategoryMapper.insert(productCategory);
        return productCategory.getId();
    }

    @Override
    public void updateProductCategory(ZcProductCategorySaveReqVO updateReqVO) {
        // 校验存在
        validateProductCategoryExists(updateReqVO.getId());
        // 校验名称唯一性（排除自身）
        validateProductCategoryValueUnique(updateReqVO.getId(), updateReqVO.getValue());
        // 更新
        ZcProductCategoryDO updateObj = BeanUtils.toBean(updateReqVO, ZcProductCategoryDO.class);
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

    private void validateProductCategoryValueUnique(Long id, String value) {
        ZcProductCategoryDO existing = productCategoryMapper.selectByValue(value);
        if (existing == null) {
            return;
        }
        if (existing.getId().equals(id)) {
            return;
        }
        throw exception(PRODUCT_CATEGORY_VALUE_EXISTS);
    }

    @Override
    public ZcProductCategoryDO getProductCategory(Long id) {
        return productCategoryMapper.selectById(id);
    }

    @Override
    public PageResult<ZcProductCategoryDO> getProductCategoryPage(ZcProductCategoryPageReqVO pageReqVO) {
        return productCategoryMapper.selectPage(pageReqVO);
    }

    @Override
    public List<ZcProductCategoryDO> getProductCategoryList(ZcProductCategoryListReqVO listReqVO) {
        return productCategoryMapper.selectList(listReqVO);
    }

}