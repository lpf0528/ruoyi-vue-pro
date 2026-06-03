package cn.iocoder.yudao.module.zc.service.warehouse;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.iocoder.yudao.module.zc.controller.admin.warehouse.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.warehouse.ZcWarehouseDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.zc.dal.mysql.warehouse.ZcWarehouseMapper;
import com.mzt.logapi.context.LogRecordContext;
import com.mzt.logapi.service.impl.DiffParseFunction;
import com.mzt.logapi.starter.annotation.LogRecord;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.module.zc.enums.LogRecordConstants.*;

/**
 * 仓库 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class ZcWarehouseServiceImpl implements ZcWarehouseService {

    @Resource
    private ZcWarehouseMapper warehouseMapper;

    @Override
    @LogRecord(type = ZC_WAREHOUSE_TYPE, subType = ZC_WAREHOUSE_CREATE_SUB_TYPE, bizNo = "{{#warehouse.id}}",
            success = ZC_WAREHOUSE_CREATE_SUCCESS)
    public Long createWarehouse(ZcWarehouseSaveReqVO createReqVO) {
        validateWarehouseNameUnique(null, createReqVO.getName());
        // 插入
        ZcWarehouseDO warehouse = BeanUtils.toBean(createReqVO, ZcWarehouseDO.class);
        warehouseMapper.insert(warehouse);
        // 若设为默认仓库，需清除其他仓库的默认标记（同一时刻至多一条默认）
        if (Boolean.TRUE.equals(createReqVO.getDefaultStatus())) {
            clearOtherDefaultStatus(warehouse.getId());
        }
        // 记录操作日志上下文
        LogRecordContext.putVariable("warehouse", warehouse);
        return warehouse.getId();
    }

    @Override
    @LogRecord(type = ZC_WAREHOUSE_TYPE, subType = ZC_WAREHOUSE_UPDATE_SUB_TYPE, bizNo = "{{#updateReqVO.id}}",
            success = ZC_WAREHOUSE_UPDATE_SUCCESS)
    public void updateWarehouse(ZcWarehouseSaveReqVO updateReqVO) {
        // 校验存在
        ZcWarehouseDO oldWarehouse = validateWarehouseExists(updateReqVO.getId());
        validateWarehouseNameUnique(updateReqVO.getId(), updateReqVO.getName());
        // 更新
        ZcWarehouseDO updateObj = BeanUtils.toBean(updateReqVO, ZcWarehouseDO.class);
        warehouseMapper.updateById(updateObj);
        // 若更新为默认仓库，需清除其他仓库的默认标记（同一时刻至多一条默认）
        if (Boolean.TRUE.equals(updateReqVO.getDefaultStatus())) {
            clearOtherDefaultStatus(updateReqVO.getId());
        }
        // 记录操作日志上下文
        LogRecordContext.putVariable(DiffParseFunction.OLD_OBJECT, BeanUtils.toBean(oldWarehouse, ZcWarehouseSaveReqVO.class));
        LogRecordContext.putVariable("warehouseName", oldWarehouse.getName());
    }

    @Override
    @LogRecord(type = ZC_WAREHOUSE_TYPE, subType = ZC_WAREHOUSE_DELETE_SUB_TYPE, bizNo = "{{#id}}",
            success = ZC_WAREHOUSE_DELETE_SUCCESS)
    public void deleteWarehouse(Long id) {
        // 校验存在
        ZcWarehouseDO warehouse = validateWarehouseExists(id);
        // 记录操作日志上下文
        LogRecordContext.putVariable("warehouseName", warehouse.getName());
        // 删除
        warehouseMapper.deleteById(id);
    }

    @Override
        public void deleteWarehouseListByIds(List<Long> ids) {
        // 删除
        warehouseMapper.deleteByIds(ids);
        }


    private ZcWarehouseDO validateWarehouseExists(Long id) {
        ZcWarehouseDO warehouse = warehouseMapper.selectById(id);
        if (warehouse == null) {
            throw exception(WAREHOUSE_NOT_EXISTS);
        }
        return warehouse;
    }

    private void validateWarehouseNameUnique(Long id, String name) {
        ZcWarehouseDO existing = warehouseMapper.selectByName(name);
        if (existing == null || existing.getId().equals(id)) {
            return;
        }
        throw exception(WAREHOUSE_NAME_EXISTS);
    }

    @Override
    public ZcWarehouseDO getWarehouse(Long id) {
        return warehouseMapper.selectById(id);
    }

    @Override
    public ZcWarehouseRespVO getWarehouseVO(Long id) {
        return warehouseMapper.selectByIdWithVO(id);
    }

    @Override
    public PageResult<ZcWarehouseDO> getWarehousePage(ZcWarehousePageReqVO pageReqVO) {
        return warehouseMapper.selectPage(pageReqVO);
    }

    @Override
    public PageResult<ZcWarehouseRespVO> getWarehousePageVO(ZcWarehousePageReqVO pageReqVO) {
        return warehouseMapper.selectPageVO(pageReqVO);
    }

    @Override
    public List<ZcWarehouseDO> getWarehouseList(ZcWarehouseListReqVO listReqVO) {
        return warehouseMapper.selectList(listReqVO);
    }

    /**
     * 将除 excludeId 之外所有默认仓库的 defaultStatus 置为 false，
     * 保证同一时刻至多只有一条默认记录
     *
     * @param excludeId 不需要被清除的仓库 ID（当前正在设为默认的那条）
     */
    private void clearOtherDefaultStatus(Long excludeId) {
        LambdaUpdateWrapper<ZcWarehouseDO> wrapper = new LambdaUpdateWrapper<ZcWarehouseDO>()
                .set(ZcWarehouseDO::getDefaultStatus, false)
                .eq(ZcWarehouseDO::getDefaultStatus, true)
                .ne(ZcWarehouseDO::getId, excludeId);
        warehouseMapper.update(wrapper);
    }

}
