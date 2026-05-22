package cn.iocoder.yudao.module.zc.service.inventoryrecord;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.zc.controller.admin.inventoryrecord.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.inventoryrecord.ZcInventoryRecordDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.zc.dal.dataobject.productbatch.ZcProductBatchDO;
import cn.iocoder.yudao.module.zc.dal.mysql.inventoryrecord.ZcInventoryRecordMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.productbatch.ZcProductBatchMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;

/**
 * 盘点记录 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class ZcInventoryRecordServiceImpl implements ZcInventoryRecordService {

    @Resource
    private ZcInventoryRecordMapper inventoryRecordMapper;
    @Resource
    private ZcProductBatchMapper productBatchMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createInventoryRecord(ZcInventoryRecordSaveReqVO createReqVO) {
        // 1. 校验批次存在
        ZcProductBatchDO batch = productBatchMapper.selectById(createReqVO.getBatchId());
        if (batch == null) {
            throw exception(PRODUCT_BATCH_NOT_EXISTS);
        }

        // 2. 插入盘点记录
        ZcInventoryRecordDO inventoryRecord = BeanUtils.toBean(createReqVO, ZcInventoryRecordDO.class);
        inventoryRecordMapper.insert(inventoryRecord);

        // 3. 同步批次剩余数量为盘点后数量
        // 盘点的意义在于用实物清点结果（newQuantity）覆盖系统记录值，此处必须同步，否则盘点数据形同虚设
        ZcProductBatchDO updateBatch = new ZcProductBatchDO();
        updateBatch.setId(batch.getId());
        updateBatch.setQuantity(createReqVO.getNewQuantity());
        productBatchMapper.updateById(updateBatch);

        return inventoryRecord.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateInventoryRecord(ZcInventoryRecordSaveReqVO updateReqVO) {
        // 1. 校验盘点记录存在
        validateInventoryRecordExists(updateReqVO.getId());

        // 2. 校验批次存在
        ZcProductBatchDO batch = productBatchMapper.selectById(updateReqVO.getBatchId());
        if (batch == null) {
            throw exception(PRODUCT_BATCH_NOT_EXISTS);
        }

        // 3. 更新盘点记录
        ZcInventoryRecordDO updateObj = BeanUtils.toBean(updateReqVO, ZcInventoryRecordDO.class);
        inventoryRecordMapper.updateById(updateObj);

        // 4. 同步批次剩余数量为修正后的盘点后数量
        ZcProductBatchDO updateBatch = new ZcProductBatchDO();
        updateBatch.setId(batch.getId());
        updateBatch.setQuantity(updateReqVO.getNewQuantity());
        productBatchMapper.updateById(updateBatch);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteInventoryRecord(Long id) {
        // 1. 校验存在并获取记录（需要 oldQuantity 用于恢复批次数量）
        ZcInventoryRecordDO record = inventoryRecordMapper.selectById(id);
        if (record == null) {
            throw exception(INVENTORY_RECORD_NOT_EXISTS);
        }

        // 2. 删除盘点记录
        inventoryRecordMapper.deleteById(id);

        // 3. 将批次剩余数量恢复为盘点前的值（撤销本次盘点对库存的影响）
        if (record.getBatchId() != null && record.getOldQuantity() != null) {
            ZcProductBatchDO updateBatch = new ZcProductBatchDO();
            updateBatch.setId(record.getBatchId());
            updateBatch.setQuantity(record.getOldQuantity());
            productBatchMapper.updateById(updateBatch);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteInventoryRecordListByIds(List<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return;
        }
        // 逐条删除以确保每条都能恢复对应批次数量
        for (Long id : ids) {
            deleteInventoryRecord(id);
        }
    }


    private void validateInventoryRecordExists(Long id) {
        if (inventoryRecordMapper.selectById(id) == null) {
            throw exception(INVENTORY_RECORD_NOT_EXISTS);
        }
    }

    @Override
    public ZcInventoryRecordDO getInventoryRecord(Long id) {
        return inventoryRecordMapper.selectById(id);
    }

    @Override
    public PageResult<ZcInventoryRecordRespVO> getInventoryRecordPage(ZcInventoryRecordPageReqVO pageReqVO) {
        return inventoryRecordMapper.selectPage(pageReqVO);
    }

}