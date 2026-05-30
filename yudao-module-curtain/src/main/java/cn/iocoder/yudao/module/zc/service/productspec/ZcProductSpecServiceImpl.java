package cn.iocoder.yudao.module.zc.service.productspec;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.iocoder.yudao.module.zc.controller.admin.productspec.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.productspec.ZcProductSpecDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.zc.dal.mysql.productspec.ZcProductSpecMapper;
import com.mzt.logapi.context.LogRecordContext;
import com.mzt.logapi.service.impl.DiffParseFunction;
import com.mzt.logapi.starter.annotation.LogRecord;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.module.zc.enums.LogRecordConstants.*;

/**
 * 产品规格 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class ZcProductSpecServiceImpl implements ZcProductSpecService {

    @Resource
    private ZcProductSpecMapper productSpecMapper;

    @Override
    @LogRecord(type = ZC_PRODUCT_SPEC_TYPE, subType = ZC_PRODUCT_SPEC_CREATE_SUB_TYPE, bizNo = "{{#productSpec.id}}",
            success = ZC_PRODUCT_SPEC_CREATE_SUCCESS)
    public Long createProductSpec(ZcProductSpecSaveReqVO createReqVO) {
        // 校验名称唯一性
        validateProductSpecValueUnique(null, createReqVO.getValue());
        // 插入
        ZcProductSpecDO productSpec = BeanUtils.toBean(createReqVO, ZcProductSpecDO.class);
        productSpecMapper.insert(productSpec);
        // 记录操作日志上下文
        LogRecordContext.putVariable("productSpec", productSpec);
        return productSpec.getId();
    }

    @Override
    @LogRecord(type = ZC_PRODUCT_SPEC_TYPE, subType = ZC_PRODUCT_SPEC_UPDATE_SUB_TYPE, bizNo = "{{#updateReqVO.id}}",
            success = ZC_PRODUCT_SPEC_UPDATE_SUCCESS)
    public void updateProductSpec(ZcProductSpecSaveReqVO updateReqVO) {
        // 校验存在
        ZcProductSpecDO oldProductSpec = validateProductSpecExists(updateReqVO.getId());
        // 校验名称唯一性（排除自身）
        validateProductSpecValueUnique(updateReqVO.getId(), updateReqVO.getValue());
        // 更新
        ZcProductSpecDO updateObj = BeanUtils.toBean(updateReqVO, ZcProductSpecDO.class);
        productSpecMapper.updateById(updateObj);
        // 记录操作日志上下文
        LogRecordContext.putVariable(DiffParseFunction.OLD_OBJECT, BeanUtils.toBean(oldProductSpec, ZcProductSpecSaveReqVO.class));
        LogRecordContext.putVariable("productSpecName", oldProductSpec.getValue());
    }

    @Override
    @LogRecord(type = ZC_PRODUCT_SPEC_TYPE, subType = ZC_PRODUCT_SPEC_DELETE_SUB_TYPE, bizNo = "{{#id}}",
            success = ZC_PRODUCT_SPEC_DELETE_SUCCESS)
    public void deleteProductSpec(Long id) {
        // 校验存在
        ZcProductSpecDO productSpec = validateProductSpecExists(id);
        // 记录操作日志上下文
        LogRecordContext.putVariable("productSpecName", productSpec.getValue());
        // 删除
        productSpecMapper.deleteById(id);
    }

    @Override
        public void deleteProductSpecListByIds(List<Long> ids) {
        // 删除
        productSpecMapper.deleteByIds(ids);
        }


    private ZcProductSpecDO validateProductSpecExists(Long id) {
        ZcProductSpecDO productSpec = productSpecMapper.selectById(id);
        if (productSpec == null) {
            throw exception(PRODUCT_SPEC_NOT_EXISTS);
        }
        return productSpec;
    }

    private void validateProductSpecValueUnique(Long id, String value) {
        ZcProductSpecDO existing = productSpecMapper.selectByValue(value);
        if (existing == null) {
            return;
        }
        if (existing.getId().equals(id)) {
            return;
        }
        throw exception(PRODUCT_SPEC_VALUE_EXISTS);
    }

    @Override
    public ZcProductSpecDO getProductSpec(Long id) {
        return productSpecMapper.selectById(id);
    }

    @Override
    public PageResult<ZcProductSpecDO> getProductSpecPage(ZcProductSpecPageReqVO pageReqVO) {
        return productSpecMapper.selectPage(pageReqVO);
    }

    @Override
    public List<ZcProductSpecDO> getProductSpecList(ZcProductSpecListReqVO listReqVO) {
        return productSpecMapper.selectList(listReqVO);
    }

}
