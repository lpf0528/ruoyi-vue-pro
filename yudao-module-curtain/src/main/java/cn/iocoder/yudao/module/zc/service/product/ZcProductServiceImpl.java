package cn.iocoder.yudao.module.zc.service.product;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.module.zc.dal.dataobject.productversion.ZcProductVersionSpcDO;
import cn.iocoder.yudao.module.zc.dal.mysql.productversion.ZcProductVersionSpcMapper;
import cn.iocoder.yudao.module.zc.controller.admin.productversion.vo.ZcProductVersionSpcRespVO;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.iocoder.yudao.module.zc.controller.admin.product.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.product.ZcProductDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.zc.dal.mysql.product.ZcProductMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.productbatch.ZcProductBatchMapper;
import com.mzt.logapi.context.LogRecordContext;
import com.mzt.logapi.service.impl.DiffParseFunction;
import com.mzt.logapi.starter.annotation.LogRecord;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.module.zc.enums.LogRecordConstants.*;

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
    @Resource
    private ZcProductVersionSpcMapper productVersionSpcMapper;

    @Override
    @LogRecord(type = ZC_PRODUCT_TYPE, subType = ZC_PRODUCT_CREATE_SUB_TYPE, bizNo = "{{#product.id}}",
            success = ZC_PRODUCT_CREATE_SUCCESS)
    public Long createProduct(ZcProductSaveReqVO createReqVO) {
        // 校验名称唯一性
        validateProductNameUnique(null, createReqVO.getName());
        // 插入
        ZcProductDO product = BeanUtils.toBean(createReqVO, ZcProductDO.class);
        productMapper.insert(product);
        // 记录操作日志上下文
        LogRecordContext.putVariable("product", product);
        return product.getId();
    }

    @Override
    @LogRecord(type = ZC_PRODUCT_TYPE, subType = ZC_PRODUCT_UPDATE_SUB_TYPE, bizNo = "{{#updateReqVO.id}}",
            success = ZC_PRODUCT_UPDATE_SUCCESS)
    public void updateProduct(ZcProductSaveReqVO updateReqVO) {
        // 校验存在
        ZcProductDO oldProduct = validateProductExists(updateReqVO.getId());
        // 校验名称唯一性（排除自身）
        validateProductNameUnique(updateReqVO.getId(), updateReqVO.getName());
        // 更新
        ZcProductDO updateObj = BeanUtils.toBean(updateReqVO, ZcProductDO.class);
        productMapper.updateById(updateObj);
        // 记录操作日志上下文
        LogRecordContext.putVariable(DiffParseFunction.OLD_OBJECT, BeanUtils.toBean(oldProduct, ZcProductSaveReqVO.class));
        LogRecordContext.putVariable("productName", oldProduct.getName());
    }

    @Override
    @LogRecord(type = ZC_PRODUCT_TYPE, subType = ZC_PRODUCT_DELETE_SUB_TYPE, bizNo = "{{#id}}",
            success = ZC_PRODUCT_DELETE_SUCCESS)
    public void deleteProduct(Long id) {
        ZcProductDO product = validateProductExists(id);
        validateProductHasNoBatch(Collections.singletonList(id));
        // 记录操作日志上下文
        LogRecordContext.putVariable("productName", product.getName());
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


    private ZcProductDO validateProductExists(Long id) {
        ZcProductDO product = productMapper.selectById(id);
        if (product == null) {
            throw exception(PRODUCT_NOT_EXISTS);
        }
        return product;
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

    @Override
    public List<ZcProductSimpleRespVO> getProductSimpleList(ZcProductListReqVO listReqVO) {
        List<ZcProductSimpleRespVO> list = productMapper.selectSimpleList(listReqVO);
        if (CollUtil.isEmpty(list)) {
            return list;
        }
        // 批量查询规格
        List<Long> versionIds = cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList(list, ZcProductSimpleRespVO::getVersionId);
        List<ZcProductVersionSpcDO> allSpcs = productVersionSpcMapper.selectList(ZcProductVersionSpcDO::getVersionId, versionIds);
        Map<Long, List<ZcProductVersionSpcDO>> spcMap = cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertMultiMap(allSpcs, ZcProductVersionSpcDO::getVersionId);
        // 填充规格
        list.forEach(vo -> vo.setSpecConfs(BeanUtils.toBean(spcMap.get(vo.getVersionId()), ZcProductVersionSpcRespVO.class)));
        return list;
    }

}
