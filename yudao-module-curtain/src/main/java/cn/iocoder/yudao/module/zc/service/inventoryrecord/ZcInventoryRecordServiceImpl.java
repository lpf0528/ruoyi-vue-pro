package cn.iocoder.yudao.module.zc.service.inventoryrecord;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.stream.Collectors;
import cn.iocoder.yudao.module.zc.controller.admin.inventoryrecord.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.inventoryrecord.ZcInventoryRecordDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.zc.dal.dataobject.productbatch.ZcProductBatchDO;
import cn.iocoder.yudao.module.zc.dal.mysql.inventoryrecord.ZcInventoryRecordMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.productbatch.ZcProductBatchMapper;
import com.mzt.logapi.context.LogRecordContext;
import com.mzt.logapi.starter.annotation.LogRecord;

import cn.iocoder.yudao.module.zc.enums.ZcInventoryRecordOperateEnum;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.module.zc.enums.LogRecordConstants.*;

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
    @LogRecord(type = ZC_INVENTORY_RECORD_TYPE, subType = ZC_INVENTORY_RECORD_CREATE_SUB_TYPE, bizNo = "{{#inventoryRecord.id}}",
            success = ZC_INVENTORY_RECORD_CREATE_SUCCESS)
    public Long createInventoryRecord(ZcInventoryRecordSaveReqVO createReqVO) {
        // 1. 校验批次存在
        ZcProductBatchDO batch = productBatchMapper.selectById(createReqVO.getBatchId());
        if (batch == null) {
            throw exception(PRODUCT_BATCH_NOT_EXISTS);
        }

        // 2. 插入盘点记录，计算变化数量（盘点前 - 盘点后），操作类型固定为盘点
        ZcInventoryRecordDO inventoryRecord = BeanUtils.toBean(createReqVO, ZcInventoryRecordDO.class);
        inventoryRecord.setChangeQuantity(createReqVO.getNewQuantity().subtract(createReqVO.getOldQuantity()));
        inventoryRecord.setOperate(ZcInventoryRecordOperateEnum.PANDIAN.name());
        inventoryRecordMapper.insert(inventoryRecord);

        // 3. 更新批次剩余数量；若本次盘点填写了备注，则用新备注覆盖上次盘点备注（保留原始非盘点备注）
        ZcProductBatchDO updateBatch = new ZcProductBatchDO();
        updateBatch.setId(batch.getId());
        updateBatch.setQuantity(createReqVO.getNewQuantity());
        if (createReqVO.getNote() != null && !createReqVO.getNote().isEmpty()) {
            String inventoryNote = "盘点：" + createReqVO.getNote();
            String newNote;
            if (batch.getNote() == null) {
                newNote = inventoryNote;
            } else {
                // 过滤掉上次的盘点行（以"盘点("开头），保留原始批次备注
                String filtered = Arrays.stream(batch.getNote().split("\n"))
                        .filter(line -> !line.startsWith("盘点："))
                        .collect(Collectors.joining("\n"));
                newNote = filtered.isEmpty() ? inventoryNote : filtered + "\n" + inventoryNote;
            }
            updateBatch.setNote(newNote);
        }
        productBatchMapper.updateById(updateBatch);

        // 4. 记录操作日志上下文
        LogRecordContext.putVariable("inventoryRecord", inventoryRecord);
        LogRecordContext.putVariable("batchNo", batch.getBatchNo());
        return inventoryRecord.getId();
    }

    @Override
    public PageResult<ZcInventoryRecordRespVO> getInventoryRecordPage(ZcInventoryRecordPageReqVO pageReqVO) {
        return inventoryRecordMapper.selectPage(pageReqVO);
    }

}
