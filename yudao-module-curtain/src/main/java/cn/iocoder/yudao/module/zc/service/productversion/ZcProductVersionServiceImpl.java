package cn.iocoder.yudao.module.zc.service.productversion;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.iocoder.yudao.module.zc.controller.admin.productversion.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.productversion.ZcProductVersionDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.zc.dal.mysql.product.ZcProductMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.productversion.ZcProductVersionMapper;
import com.mzt.logapi.context.LogRecordContext;
import com.mzt.logapi.service.impl.DiffParseFunction;
import com.mzt.logapi.starter.annotation.LogRecord;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.module.zc.enums.LogRecordConstants.*;

/**
 * 产品版本 Service 实现类
 *
 * @author 01Coder
 */
@Service
@Validated
public class ZcProductVersionServiceImpl implements ZcProductVersionService {

    @Resource
    private ZcProductVersionMapper productVersionMapper;
    @Resource
    private ZcProductMapper productMapper;

    @Override
    @LogRecord(type = ZC_PRODUCT_VERSION_TYPE, subType = ZC_PRODUCT_VERSION_CREATE_SUB_TYPE, bizNo = "{{#productVersion.id}}",
            success = ZC_PRODUCT_VERSION_CREATE_SUCCESS)
    public Long createProductVersion(ZcProductVersionSaveReqVO createReqVO) {
        // 校验名称唯一性
        validateProductVersionNameUnique(null, createReqVO.getName());
        // 插入
        ZcProductVersionDO productVersion = BeanUtils.toBean(createReqVO, ZcProductVersionDO.class);
        productVersionMapper.insert(productVersion);
        // 记录操作日志上下文
        LogRecordContext.putVariable("productVersion", productVersion);
        return productVersion.getId();
    }

    @Override
    @LogRecord(type = ZC_PRODUCT_VERSION_TYPE, subType = ZC_PRODUCT_VERSION_UPDATE_SUB_TYPE, bizNo = "{{#updateReqVO.id}}",
            success = ZC_PRODUCT_VERSION_UPDATE_SUCCESS)
    public void updateProductVersion(ZcProductVersionSaveReqVO updateReqVO) {
        // 校验存在
        ZcProductVersionDO oldProductVersion = validateProductVersionExists(updateReqVO.getId());
        // 校验名称唯一性（排除自身）
        validateProductVersionNameUnique(updateReqVO.getId(), updateReqVO.getName());
        // 更新
        ZcProductVersionDO updateObj = BeanUtils.toBean(updateReqVO, ZcProductVersionDO.class);
        productVersionMapper.updateById(updateObj);
        // 记录操作日志上下文
        LogRecordContext.putVariable(DiffParseFunction.OLD_OBJECT, BeanUtils.toBean(oldProductVersion, ZcProductVersionSaveReqVO.class));
        LogRecordContext.putVariable("productVersionName", oldProductVersion.getName());
    }

    @Override
    @LogRecord(type = ZC_PRODUCT_VERSION_TYPE, subType = ZC_PRODUCT_VERSION_DELETE_SUB_TYPE, bizNo = "{{#id}}",
            success = ZC_PRODUCT_VERSION_DELETE_SUCCESS)
    public void deleteProductVersion(Long id) {
        // 校验存在
        ZcProductVersionDO productVersion = validateProductVersionExists(id);
        // 校验该版本下是否存在绑定产品，存在则禁止删除
        if (productMapper.countByVersionId(id) > 0) {
            throw exception(PRODUCT_VERSION_HAS_PRODUCTS);
        }
        // 记录操作日志上下文
        LogRecordContext.putVariable("productVersionName", productVersion.getName());
        // 删除
        productVersionMapper.deleteById(id);
    }

    @Override
    public void deleteProductVersionListByIds(List<Long> ids) {
        // 校验每个版本是否存在绑定产品
        ids.forEach(id -> {
            if (productMapper.countByVersionId(id) > 0) {
                throw exception(PRODUCT_VERSION_HAS_PRODUCTS);
            }
        });
        // 删除
        productVersionMapper.deleteByIds(ids);
    }


    private ZcProductVersionDO validateProductVersionExists(Long id) {
        ZcProductVersionDO productVersion = productVersionMapper.selectById(id);
        if (productVersion == null) {
            throw exception(PRODUCT_VERSION_NOT_EXISTS);
        }
        return productVersion;
    }

    /**
     * 校验版本名称唯一性
     *
     * @param id   排除的记录 ID（更新时传入，新增时传 null）
     * @param name 待校验的名称
     */
    private void validateProductVersionNameUnique(Long id, String name) {
        ZcProductVersionDO existing = productVersionMapper.selectByName(name);
        if (existing == null) {
            return;
        }
        // 更新场景：查到的记录是自身，允许通过
        if (existing.getId().equals(id)) {
            return;
        }
        throw exception(PRODUCT_VERSION_NAME_EXISTS);
    }

    @Override
    public ZcProductVersionDO getProductVersion(Long id) {
        return productVersionMapper.selectById(id);
    }

    @Override
    public PageResult<ZcProductVersionRespVO> getProductVersionPage(ZcProductVersionPageReqVO pageReqVO) {
        return productVersionMapper.selectPage(pageReqVO);
    }

    @Override
    public List<ZcProductVersionDO> getProductVersionList(ZcProductVersionListReqVO listReqVO) {
        return productVersionMapper.selectList(listReqVO);
    }

}
