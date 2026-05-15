package cn.iocoder.yudao.module.zc.service.productspec;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.zc.controller.admin.productspec.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.productspec.ZcProductSpecDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.zc.dal.mysql.productspec.ZcProductSpecMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;

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
    public Long createProductSpec(ZcProductSpecSaveReqVO createReqVO) {
        // 插入
        ZcProductSpecDO productSpec = BeanUtils.toBean(createReqVO, ZcProductSpecDO.class);
        productSpecMapper.insert(productSpec);

        // 返回
        return productSpec.getId();
    }

    @Override
    public void updateProductSpec(ZcProductSpecSaveReqVO updateReqVO) {
        // 校验存在
        validateProductSpecExists(updateReqVO.getId());
        // 更新
        ZcProductSpecDO updateObj = BeanUtils.toBean(updateReqVO, ZcProductSpecDO.class);
        productSpecMapper.updateById(updateObj);
    }

    @Override
    public void deleteProductSpec(Long id) {
        // 校验存在
        validateProductSpecExists(id);
        // 删除
        productSpecMapper.deleteById(id);
    }

    @Override
        public void deleteProductSpecListByIds(List<Long> ids) {
        // 删除
        productSpecMapper.deleteByIds(ids);
        }


    private void validateProductSpecExists(Long id) {
        if (productSpecMapper.selectById(id) == null) {
            throw exception(PRODUCT_SPEC_NOT_EXISTS);
        }
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