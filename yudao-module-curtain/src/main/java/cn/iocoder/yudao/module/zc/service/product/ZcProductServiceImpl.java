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

    @Override
    public Long createProduct(ZcProductSaveReqVO createReqVO) {
        // 插入
        ZcProductDO product = BeanUtils.toBean(createReqVO, ZcProductDO.class);
        productMapper.insert(product);

        // 返回
        return product.getId();
    }

    @Override
    public void updateProduct(ZcProductSaveReqVO updateReqVO) {
        // 校验存在
        validateProductExists(updateReqVO.getId());
        // 更新
        ZcProductDO updateObj = BeanUtils.toBean(updateReqVO, ZcProductDO.class);
        productMapper.updateById(updateObj);
    }

    @Override
    public void deleteProduct(Long id) {
        // 校验存在
        validateProductExists(id);
        // 删除
        productMapper.deleteById(id);
    }

    @Override
        public void deleteProductListByIds(List<Long> ids) {
        // 删除
        productMapper.deleteByIds(ids);
        }


    private void validateProductExists(Long id) {
        if (productMapper.selectById(id) == null) {
            throw exception(PRODUCT_NOT_EXISTS);
        }
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