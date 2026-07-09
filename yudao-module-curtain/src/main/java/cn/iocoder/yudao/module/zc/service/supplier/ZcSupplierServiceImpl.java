package cn.iocoder.yudao.module.zc.service.supplier;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.iocoder.yudao.module.zc.controller.admin.supplier.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.supplier.ZcSupplierDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.zc.dal.mysql.supplier.ZcSupplierMapper;
import com.mzt.logapi.context.LogRecordContext;
import com.mzt.logapi.service.impl.DiffParseFunction;
import com.mzt.logapi.starter.annotation.LogRecord;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.module.zc.enums.LogRecordConstants.*;

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
    @LogRecord(type = ZC_SUPPLIER_TYPE, subType = ZC_SUPPLIER_CREATE_SUB_TYPE, bizNo = "{{#supplier.id}}",
            success = ZC_SUPPLIER_CREATE_SUCCESS)
    public Long createSupplier(ZcSupplierSaveReqVO createReqVO) {
        validateSupplierShortNameUnique(null, createReqVO.getShortName());
        // 插入
        ZcSupplierDO supplier = BeanUtils.toBean(createReqVO, ZcSupplierDO.class);
        supplierMapper.insert(supplier);
        // 记录操作日志上下文
        LogRecordContext.putVariable("supplier", supplier);
        return supplier.getId();
    }

    @Override
    @LogRecord(type = ZC_SUPPLIER_TYPE, subType = ZC_SUPPLIER_UPDATE_SUB_TYPE, bizNo = "{{#updateReqVO.id}}",
            success = ZC_SUPPLIER_UPDATE_SUCCESS)
    public void updateSupplier(ZcSupplierSaveReqVO updateReqVO) {
        // 校验存在
        ZcSupplierDO oldSupplier = validateSupplierExists(updateReqVO.getId());
        validateSupplierShortNameUnique(updateReqVO.getId(), updateReqVO.getShortName());
        // 更新
        ZcSupplierDO updateObj = BeanUtils.toBean(updateReqVO, ZcSupplierDO.class);
        supplierMapper.updateById(updateObj);
        // 记录操作日志上下文
        LogRecordContext.putVariable(DiffParseFunction.OLD_OBJECT, BeanUtils.toBean(oldSupplier, ZcSupplierSaveReqVO.class));
        LogRecordContext.putVariable("supplierName", oldSupplier.getShortName());
    }

    @Override
    @LogRecord(type = ZC_SUPPLIER_TYPE, subType = ZC_SUPPLIER_DELETE_SUB_TYPE, bizNo = "{{#id}}",
            success = ZC_SUPPLIER_DELETE_SUCCESS)
    public void deleteSupplier(Long id) {
        // 校验存在
        ZcSupplierDO supplier = validateSupplierExists(id);
        // 记录操作日志上下文
        LogRecordContext.putVariable("supplierName", supplier.getShortName());
        // 删除
        supplierMapper.deleteById(id);
    }

    @Override
        public void deleteSupplierListByIds(List<Long> ids) {
        // 删除
        supplierMapper.deleteByIds(ids);
        }


    private ZcSupplierDO validateSupplierExists(Long id) {
        ZcSupplierDO supplier = supplierMapper.selectById(id);
        if (supplier == null) {
            throw exception(SUPPLIER_NOT_EXISTS);
        }
        return supplier;
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
