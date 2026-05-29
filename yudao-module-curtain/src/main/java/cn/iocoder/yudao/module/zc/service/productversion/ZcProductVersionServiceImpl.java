package cn.iocoder.yudao.module.zc.service.productversion;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.zc.controller.admin.productversion.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.productversion.ZcProductVersionDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.zc.dal.mysql.product.ZcProductMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.productversion.ZcProductVersionMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;

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
    public Long createProductVersion(ZcProductVersionSaveReqVO createReqVO) {
        // 校验名称唯一性
        validateProductVersionNameUnique(null, createReqVO.getName());
        // 插入
        ZcProductVersionDO productVersion = BeanUtils.toBean(createReqVO, ZcProductVersionDO.class);
        productVersionMapper.insert(productVersion);
        return productVersion.getId();
    }

    @Override
    public void updateProductVersion(ZcProductVersionSaveReqVO updateReqVO) {
        // 校验存在
        validateProductVersionExists(updateReqVO.getId());
        // 校验名称唯一性（排除自身）
        validateProductVersionNameUnique(updateReqVO.getId(), updateReqVO.getName());
        // 更新
        ZcProductVersionDO updateObj = BeanUtils.toBean(updateReqVO, ZcProductVersionDO.class);
        productVersionMapper.updateById(updateObj);
    }

    @Override
    public void deleteProductVersion(Long id) {
        // 校验存在
        validateProductVersionExists(id);
        // 校验该版本下是否存在绑定产品，存在则禁止删除
        if (productMapper.countByVersionId(id) > 0) {
            throw exception(PRODUCT_VERSION_HAS_PRODUCTS);
        }
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


    private void validateProductVersionExists(Long id) {
        if (productVersionMapper.selectById(id) == null) {
            throw exception(PRODUCT_VERSION_NOT_EXISTS);
        }
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