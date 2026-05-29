package cn.iocoder.yudao.module.zc.service.product;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.zc.controller.admin.product.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.product.ZcProductDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.zc.dal.mysql.product.ZcProductMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.productbatch.ZcProductBatchMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;

/**
 * 产品 Service 实现类
 *
 * @author 01Coder
 */
@Service
@Validated
public class ZcProductServiceImpl implements ZcProductService {

    @Resource
    private ZcProductMapper productMapper;
    @Resource
    private ZcProductBatchMapper productBatchMapper;

    @Override
    public Long createProduct(ZcProductSaveReqVO createReqVO) {
        // 校验名称唯一性
        validateProductNameUnique(null, createReqVO.getName());
        // 插入
        ZcProductDO product = BeanUtils.toBean(createReqVO, ZcProductDO.class);
        productMapper.insert(product);
        return product.getId();
    }

    @Override
    public void updateProduct(ZcProductSaveReqVO updateReqVO) {
        // 校验存在
        validateProductExists(updateReqVO.getId());
        // 校验名称唯一性（排除自身）
        validateProductNameUnique(updateReqVO.getId(), updateReqVO.getName());
        // 更新
        ZcProductDO updateObj = BeanUtils.toBean(updateReqVO, ZcProductDO.class);
        productMapper.updateById(updateObj);
    }

    @Override
    public void deleteProduct(Long id) {
        validateProductExists(id);
        validateProductHasNoBatch(Collections.singletonList(id));
        productMapper.deleteById(id);
    }

    @Override
    public void deleteProductListByIds(List<Long> ids) {
        validateProductHasNoBatch(ids);
        productMapper.deleteByIds(ids);
    }

    private void validateProductHasNoBatch(List<Long> ids) {
        List<Long> usedIds = productBatchMapper.selectProductIdsWithBatch(ids);
        if (CollUtil.isNotEmpty(usedIds)) {
            throw exception(PRODUCT_HAS_BATCH);
        }
    }


    private void validateProductExists(Long id) {
        if (productMapper.selectById(id) == null) {
            throw exception(PRODUCT_NOT_EXISTS);
        }
    }

    /**
     * 校验产品名称唯一性
     *
     * @param id   排除的记录 ID（更新时传入，新增时传 null）
     * @param name 待校验的名称
     */
    private void validateProductNameUnique(Long id, String name) {
        ZcProductDO existing = productMapper.selectByName(name);
        if (existing == null) {
            return;
        }
        // 更新场景：查到的记录是自身，允许通过
        if (existing.getId().equals(id)) {
            return;
        }
        throw exception(PRODUCT_NAME_EXISTS);
    }

    @Override
    public ZcProductDO getProduct(Long id) {
        return productMapper.selectById(id);
    }

    @Override
    public PageResult<ZcProductRespVO> getProductPage(ZcProductPageReqVO pageReqVO) {
        return productMapper.selectPage(pageReqVO);
    }

    @Override
    public List<ZcProductDO> getProductList(ZcProductListReqVO listReqVO) {
        return productMapper.selectList(listReqVO);
    }

}