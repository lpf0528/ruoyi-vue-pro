package cn.iocoder.yudao.module.zc.service.productspec;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.zc.controller.admin.productspec.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.productspec.zcProductSpecDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.zc.dal.mysql.productspec.zcProductSpecMapper;

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
public class zcProductSpecServiceImpl implements zcProductSpecService {

    @Resource
    private zcProductSpecMapper zcProductSpecMapper;

    @Override
    public Long createzcProductSpec(zcProductSpecSaveReqVO createReqVO) {
        // 插入
        zcProductSpecDO zcProductSpec = BeanUtils.toBean(createReqVO, zcProductSpecDO.class);
        zcProductSpecMapper.insert(zcProductSpec);

        // 返回
        return zcProductSpec.getId();
    }

    @Override
    public void updatezcProductSpec(zcProductSpecSaveReqVO updateReqVO) {
        // 校验存在
        validatezcProductSpecExists(updateReqVO.getId());
        // 更新
        zcProductSpecDO updateObj = BeanUtils.toBean(updateReqVO, zcProductSpecDO.class);
        zcProductSpecMapper.updateById(updateObj);
    }

    @Override
    public void deletezcProductSpec(Long id) {
        // 校验存在
        validatezcProductSpecExists(id);
        // 删除
        zcProductSpecMapper.deleteById(id);
    }

    @Override
        public void deletezcProductSpecListByIds(List<Long> ids) {
        // 删除
        zcProductSpecMapper.deleteByIds(ids);
        }


    private void validatezcProductSpecExists(Long id) {
        if (zcProductSpecMapper.selectById(id) == null) {
            throw exception(ZC_PRODUCT_SPEC_NOT_EXISTS);
        }
    }

    @Override
    public zcProductSpecDO getzcProductSpec(Long id) {
        return zcProductSpecMapper.selectById(id);
    }

    @Override
    public PageResult<zcProductSpecDO> getzcProductSpecPage(zcProductSpecPageReqVO pageReqVO) {
        return zcProductSpecMapper.selectPage(pageReqVO);
    }

}