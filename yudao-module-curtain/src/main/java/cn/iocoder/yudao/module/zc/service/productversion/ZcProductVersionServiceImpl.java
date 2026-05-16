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

import cn.iocoder.yudao.module.zc.dal.mysql.productversion.ZcProductVersionMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;

/**
 * 产品版本 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class ZcProductVersionServiceImpl implements ZcProductVersionService {

    @Resource
    private ZcProductVersionMapper productVersionMapper;

    @Override
    public Long createProductVersion(ZcProductVersionSaveReqVO createReqVO) {
        // 插入
        ZcProductVersionDO productVersion = BeanUtils.toBean(createReqVO, ZcProductVersionDO.class);
        productVersionMapper.insert(productVersion);

        // 返回
        return productVersion.getId();
    }

    @Override
    public void updateProductVersion(ZcProductVersionSaveReqVO updateReqVO) {
        // 校验存在
        validateProductVersionExists(updateReqVO.getId());
        // 更新
        ZcProductVersionDO updateObj = BeanUtils.toBean(updateReqVO, ZcProductVersionDO.class);
        productVersionMapper.updateById(updateObj);
    }

    @Override
    public void deleteProductVersion(Long id) {
        // 校验存在
        validateProductVersionExists(id);
        // 删除
        productVersionMapper.deleteById(id);
    }

    @Override
        public void deleteProductVersionListByIds(List<Long> ids) {
        // 删除
        productVersionMapper.deleteByIds(ids);
        }


    private void validateProductVersionExists(Long id) {
        if (productVersionMapper.selectById(id) == null) {
            throw exception(PRODUCT_VERSION_NOT_EXISTS);
        }
    }

    @Override
    public ZcProductVersionDO getProductVersion(Long id) {
        return productVersionMapper.selectById(id);
    }

    @Override
    public PageResult<ZcProductVersionDO> getProductVersionPage(ZcProductVersionPageReqVO pageReqVO) {
        return productVersionMapper.selectPage(pageReqVO);
    }

    @Override
    public List<ZcProductVersionDO> getProductVersionList(ZcProductVersionListReqVO listReqVO) {
        return productVersionMapper.selectList(listReqVO);
    }

}