package cn.iocoder.yudao.module.zc.service.brand;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.iocoder.yudao.module.zc.controller.admin.brand.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.brand.ZcBrandDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.zc.dal.mysql.brand.ZcBrandMapper;
import com.mzt.logapi.context.LogRecordContext;
import com.mzt.logapi.service.impl.DiffParseFunction;
import com.mzt.logapi.starter.annotation.LogRecord;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.module.zc.enums.LogRecordConstants.*;

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
    @LogRecord(type = ZC_BRAND_TYPE, subType = ZC_BRAND_CREATE_SUB_TYPE, bizNo = "{{#brand.id}}",
            success = ZC_BRAND_CREATE_SUCCESS)
    public Long createBrand(ZcBrandSaveReqVO createReqVO) {
        validateBrandNameUnique(null, createReqVO.getName());
        // 插入
        ZcBrandDO brand = BeanUtils.toBean(createReqVO, ZcBrandDO.class);
        brandMapper.insert(brand);
        // 若设为默认，清除其他品牌的默认标志（排除自身）
        if (Boolean.TRUE.equals(createReqVO.getIsDefault())) {
            brandMapper.clearDefault(brand.getId());
        }
        // 记录操作日志上下文
        LogRecordContext.putVariable("brand", brand);
        return brand.getId();
    }

    @Override
    @LogRecord(type = ZC_BRAND_TYPE, subType = ZC_BRAND_UPDATE_SUB_TYPE, bizNo = "{{#updateReqVO.id}}",
            success = ZC_BRAND_UPDATE_SUCCESS)
    public void updateBrand(ZcBrandSaveReqVO updateReqVO) {
        // 校验存在
        ZcBrandDO oldBrand = validateBrandExists(updateReqVO.getId());
        validateBrandNameUnique(updateReqVO.getId(), updateReqVO.getName());
        // 若设为默认，清除其他品牌的默认标志（排除自身）
        if (Boolean.TRUE.equals(updateReqVO.getIsDefault())) {
            brandMapper.clearDefault(updateReqVO.getId());
        }
        // 更新
        ZcBrandDO updateObj = BeanUtils.toBean(updateReqVO, ZcBrandDO.class);
        brandMapper.updateById(updateObj);
        // 记录操作日志上下文
        LogRecordContext.putVariable(DiffParseFunction.OLD_OBJECT, BeanUtils.toBean(oldBrand, ZcBrandSaveReqVO.class));
        LogRecordContext.putVariable("brandName", oldBrand.getName());
    }

    @Override
    @LogRecord(type = ZC_BRAND_TYPE, subType = ZC_BRAND_DELETE_SUB_TYPE, bizNo = "{{#id}}",
            success = ZC_BRAND_DELETE_SUCCESS)
    public void deleteBrand(Long id) {
        // 校验存在
        ZcBrandDO brand = validateBrandExists(id);
        // 记录操作日志上下文
        LogRecordContext.putVariable("brandName", brand.getName());
        // 删除
        brandMapper.deleteById(id);
    }

    @Override
        public void deleteBrandListByIds(List<Long> ids) {
        // 删除
        brandMapper.deleteByIds(ids);
        }


    private ZcBrandDO validateBrandExists(Long id) {
        ZcBrandDO brand = brandMapper.selectById(id);
        if (brand == null) {
            throw exception(BRAND_NOT_EXISTS);
        }
        return brand;
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
