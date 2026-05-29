package cn.iocoder.yudao.module.zc.service.supplier;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.zc.controller.admin.supplier.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.supplier.ZcSupplierDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.zc.dal.mysql.supplier.ZcSupplierMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;

/**
 * 供应商 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class ZcSupplierServiceImpl implements ZcSupplierService {

    @Resource
    private ZcSupplierMapper supplierMapper;

    @Override
    public Long createSupplier(ZcSupplierSaveReqVO createReqVO) {
        validateSupplierShortNameUnique(null, createReqVO.getShortName());
        // 插入
        ZcSupplierDO supplier = BeanUtils.toBean(createReqVO, ZcSupplierDO.class);
        supplierMapper.insert(supplier);
        return supplier.getId();
    }

    @Override
    public void updateSupplier(ZcSupplierSaveReqVO updateReqVO) {
        // 校验存在
        validateSupplierExists(updateReqVO.getId());
        validateSupplierShortNameUnique(updateReqVO.getId(), updateReqVO.getShortName());
        // 更新
        ZcSupplierDO updateObj = BeanUtils.toBean(updateReqVO, ZcSupplierDO.class);
        supplierMapper.updateById(updateObj);
    }

    @Override
    public void deleteSupplier(Long id) {
        // 校验存在
        validateSupplierExists(id);
        // 删除
        supplierMapper.deleteById(id);
    }

    @Override
        public void deleteSupplierListByIds(List<Long> ids) {
        // 删除
        supplierMapper.deleteByIds(ids);
        }


    private void validateSupplierExists(Long id) {
        if (supplierMapper.selectById(id) == null) {
            throw exception(SUPPLIER_NOT_EXISTS);
        }
    }

    private void validateSupplierShortNameUnique(Long id, String shortName) {
        ZcSupplierDO existing = supplierMapper.selectByShortName(shortName);
        if (existing == null || existing.getId().equals(id)) {
            return;
        }
        throw exception(SUPPLIER_SHORT_NAME_EXISTS);
    }

    @Override
    public ZcSupplierDO getSupplier(Long id) {
        return supplierMapper.selectById(id);
    }

    @Override
    public PageResult<ZcSupplierDO> getSupplierPage(ZcSupplierPageReqVO pageReqVO) {
        return supplierMapper.selectPage(pageReqVO);
    }

    @Override
    public List<ZcSupplierDO> getSupplierList(ZcSupplierListReqVO listReqVO) {
        return supplierMapper.selectList(listReqVO);
    }

}