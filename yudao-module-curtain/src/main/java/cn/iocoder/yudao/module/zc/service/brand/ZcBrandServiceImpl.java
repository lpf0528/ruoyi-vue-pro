package cn.iocoder.yudao.module.zc.service.brand;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.zc.controller.admin.brand.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.brand.ZcBrandDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.zc.dal.mysql.brand.ZcBrandMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;

/**
 * 品牌 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class ZcBrandServiceImpl implements ZcBrandService {

    @Resource
    private ZcBrandMapper brandMapper;

    @Override
    public Long createBrand(ZcBrandSaveReqVO createReqVO) {
        validateBrandNameUnique(null, createReqVO.getName());
        // 插入
        ZcBrandDO brand = BeanUtils.toBean(createReqVO, ZcBrandDO.class);
        brandMapper.insert(brand);
        return brand.getId();
    }

    @Override
    public void updateBrand(ZcBrandSaveReqVO updateReqVO) {
        // 校验存在
        validateBrandExists(updateReqVO.getId());
        validateBrandNameUnique(updateReqVO.getId(), updateReqVO.getName());
        // 更新
        ZcBrandDO updateObj = BeanUtils.toBean(updateReqVO, ZcBrandDO.class);
        brandMapper.updateById(updateObj);
    }

    @Override
    public void deleteBrand(Long id) {
        // 校验存在
        validateBrandExists(id);
        // 删除
        brandMapper.deleteById(id);
    }

    @Override
        public void deleteBrandListByIds(List<Long> ids) {
        // 删除
        brandMapper.deleteByIds(ids);
        }


    private void validateBrandExists(Long id) {
        if (brandMapper.selectById(id) == null) {
            throw exception(BRAND_NOT_EXISTS);
        }
    }

    private void validateBrandNameUnique(Long id, String name) {
        ZcBrandDO existing = brandMapper.selectByName(name);
        if (existing == null || existing.getId().equals(id)) {
            return;
        }
        throw exception(BRAND_NAME_EXISTS);
    }

    @Override
    public ZcBrandDO getBrand(Long id) {
        return brandMapper.selectById(id);
    }

    @Override
    public PageResult<ZcBrandDO> getBrandPage(ZcBrandPageReqVO pageReqVO) {
        return brandMapper.selectPage(pageReqVO);
    }

    @Override
    public List<ZcBrandDO> getBrandList(ZcBrandListReqVO listReqVO) {
        return brandMapper.selectList(listReqVO);
    }

}