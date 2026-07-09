package cn.iocoder.yudao.module.zc.service.productcategory;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.iocoder.yudao.module.zc.controller.admin.productcategory.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.productcategory.ZcProductCategoryDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.zc.dal.mysql.productcategory.ZcProductCategoryMapper;
import com.mzt.logapi.context.LogRecordContext;
import com.mzt.logapi.service.impl.DiffParseFunction;
import com.mzt.logapi.starter.annotation.LogRecord;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.module.zc.enums.LogRecordConstants.*;

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
    @LogRecord(type = ZC_PRODUCT_CATEGORY_TYPE, subType = ZC_PRODUCT_CATEGORY_CREATE_SUB_TYPE, bizNo = "{{#productCategory.id}}",
            success = ZC_PRODUCT_CATEGORY_CREATE_SUCCESS)
    public Long createProductCategory(ZcProductCategorySaveReqVO createReqVO) {
        // 校验名称唯一性
        validateProductCategoryValueUnique(null, createReqVO.getValue());
        // 插入
        ZcProductCategoryDO productCategory = BeanUtils.toBean(createReqVO, ZcProductCategoryDO.class);
        productCategoryMapper.insert(productCategory);
        // 记录操作日志上下文
        LogRecordContext.putVariable("productCategory", productCategory);
        return productCategory.getId();
    }

    @Override
    @LogRecord(type = ZC_PRODUCT_CATEGORY_TYPE, subType = ZC_PRODUCT_CATEGORY_UPDATE_SUB_TYPE, bizNo = "{{#updateReqVO.id}}",
            success = ZC_PRODUCT_CATEGORY_UPDATE_SUCCESS)
    public void updateProductCategory(ZcProductCategorySaveReqVO updateReqVO) {
        // 校验存在
        ZcProductCategoryDO oldProductCategory = validateProductCategoryExists(updateReqVO.getId());
        // 校验名称唯一性（排除自身）
        validateProductCategoryValueUnique(updateReqVO.getId(), updateReqVO.getValue());
        // 更新
        ZcProductCategoryDO updateObj = BeanUtils.toBean(updateReqVO, ZcProductCategoryDO.class);
        productCategoryMapper.updateById(updateObj);
        // 记录操作日志上下文
        LogRecordContext.putVariable(DiffParseFunction.OLD_OBJECT, BeanUtils.toBean(oldProductCategory, ZcProductCategorySaveReqVO.class));
        LogRecordContext.putVariable("productCategoryName", oldProductCategory.getValue());
    }

    @Override
    @LogRecord(type = ZC_PRODUCT_CATEGORY_TYPE, subType = ZC_PRODUCT_CATEGORY_DELETE_SUB_TYPE, bizNo = "{{#id}}",
            success = ZC_PRODUCT_CATEGORY_DELETE_SUCCESS)
    public void deleteProductCategory(Long id) {
        // 校验存在
        ZcProductCategoryDO productCategory = validateProductCategoryExists(id);
        // 记录操作日志上下文
        LogRecordContext.putVariable("productCategoryName", productCategory.getValue());
        // 删除
        productCategoryMapper.deleteById(id);
    }

    @Override
        public void deleteProductCategoryListByIds(List<Long> ids) {
        // 删除
        productCategoryMapper.deleteByIds(ids);
        }


    private ZcProductCategoryDO validateProductCategoryExists(Long id) {
        ZcProductCategoryDO productCategory = productCategoryMapper.selectById(id);
        if (productCategory == null) {
            throw exception(PRODUCT_CATEGORY_NOT_EXISTS);
        }
        return productCategory;
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
