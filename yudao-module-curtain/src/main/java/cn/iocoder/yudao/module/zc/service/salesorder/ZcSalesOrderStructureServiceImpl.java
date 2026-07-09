package cn.iocoder.yudao.module.zc.service.salesorder;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZcSalesOrderStructureDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.zc.dal.mysql.salesorder.ZcSalesOrderStructureMapper;
import com.mzt.logapi.context.LogRecordContext;
import com.mzt.logapi.service.impl.DiffParseFunction;
import com.mzt.logapi.starter.annotation.LogRecord;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.module.zc.enums.LogRecordConstants.*;

/**
 * 成品订单-结构 Service 实现类
 *
 * @author 01Coder
 */
@Service
@Validated
public class ZcSalesOrderStructureServiceImpl implements ZcSalesOrderStructureService {

    @Resource
    private ZcSalesOrderStructureMapper salesOrderStructureMapper;

    @Override
    @LogRecord(type = ZC_SALES_ORDER_STRUCTURE_TYPE, subType = ZC_SALES_ORDER_STRUCTURE_CREATE_SUB_TYPE, bizNo = "{{#orderStructure.id}}",
            success = ZC_SALES_ORDER_STRUCTURE_CREATE_SUCCESS)
    public Long createSalesOrderStructure(ZcSalesOrderStructureSaveReqVO createReqVO) {
        // 插入
        ZcSalesOrderStructureDO orderStructure = BeanUtils.toBean(createReqVO, ZcSalesOrderStructureDO.class);
        salesOrderStructureMapper.insert(orderStructure);
        // 记录操作日志上下文
        LogRecordContext.putVariable("orderStructure", orderStructure);
        return orderStructure.getId();
    }

    @Override
    @LogRecord(type = ZC_SALES_ORDER_STRUCTURE_TYPE, subType = ZC_SALES_ORDER_STRUCTURE_UPDATE_SUB_TYPE, bizNo = "{{#updateReqVO.id}}",
            success = ZC_SALES_ORDER_STRUCTURE_UPDATE_SUCCESS)
    public void updateSalesOrderStructure(ZcSalesOrderStructureSaveReqVO updateReqVO) {
        // 校验存在
        ZcSalesOrderStructureDO oldOrderStructure = validateSalesOrderStructureExists(updateReqVO.getId());
        // 更新
        ZcSalesOrderStructureDO updateObj = BeanUtils.toBean(updateReqVO, ZcSalesOrderStructureDO.class);
        salesOrderStructureMapper.updateById(updateObj);
        // 记录操作日志上下文
        LogRecordContext.putVariable(DiffParseFunction.OLD_OBJECT, BeanUtils.toBean(oldOrderStructure, ZcSalesOrderStructureSaveReqVO.class));
        LogRecordContext.putVariable("orderStructureId", oldOrderStructure.getId());
    }

    @Override
    @LogRecord(type = ZC_SALES_ORDER_STRUCTURE_TYPE, subType = ZC_SALES_ORDER_STRUCTURE_DELETE_SUB_TYPE, bizNo = "{{#id}}",
            success = ZC_SALES_ORDER_STRUCTURE_DELETE_SUCCESS)
    public void deleteSalesOrderStructure(Long id) {
        // 校验存在
        ZcSalesOrderStructureDO orderStructure = validateSalesOrderStructureExists(id);
        // 记录操作日志上下文
        LogRecordContext.putVariable("orderStructureId", orderStructure.getId());
        // 删除
        salesOrderStructureMapper.deleteById(id);
    }

    @Override
    public void deleteSalesOrderStructureListByIds(List<Long> ids) {
        // 删除
        salesOrderStructureMapper.deleteByIds(ids);
    }

    private ZcSalesOrderStructureDO validateSalesOrderStructureExists(Long id) {
        ZcSalesOrderStructureDO orderStructure = salesOrderStructureMapper.selectById(id);
        if (orderStructure == null) {
            throw exception(SALES_ORDER_STRUCTURE_NOT_EXISTS);
        }
        return orderStructure;
    }

    @Override
    public ZcSalesOrderStructureDO getSalesOrderStructure(Long id) {
        return salesOrderStructureMapper.selectById(id);
    }

    @Override
    public PageResult<ZcSalesOrderStructureDO> getSalesOrderStructurePage(ZcSalesOrderStructurePageReqVO pageReqVO) {
        return salesOrderStructureMapper.selectPage(pageReqVO);
    }

}
