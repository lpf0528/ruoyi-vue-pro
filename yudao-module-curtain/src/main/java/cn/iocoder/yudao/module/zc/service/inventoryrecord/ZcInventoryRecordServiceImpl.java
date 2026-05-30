package cn.iocoder.yudao.module.zc.service.inventoryrecord;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import cn.iocoder.yudao.module.zc.controller.admin.inventoryrecord.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.inventoryrecord.ZcInventoryRecordDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.zc.dal.dataobject.productbatch.ZcProductBatchDO;
import cn.iocoder.yudao.module.zc.dal.mysql.inventoryrecord.ZcInventoryRecordMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.productbatch.ZcProductBatchMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
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

    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

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

        // 3. 更新批次剩余数量，并追加盘点备注（格式："\n盘点(时间)：备注"）
        String timeStr = LocalDateTime.now().format(DATETIME_FORMATTER);
        String appendNote = "盘点(" + timeStr + ")：" + (createReqVO.getNote() != null ? createReqVO.getNote() : "");
        String newNote = batch.getNote() == null ? appendNote.trim() : appendNote.trim() + "\n" + batch.getNote();

        ZcProductBatchDO updateBatch = new ZcProductBatchDO();
        updateBatch.setId(batch.getId());
        updateBatch.setQuantity(createReqVO.getNewQuantity());
        updateBatch.setNote(newNote);
        productBatchMapper.updateById(updateBatch);

        return inventoryRecord.getId();
    }

    @Override
    public PageResult<ZcInventoryRecordRespVO> getInventoryRecordPage(ZcInventoryRecordPageReqVO pageReqVO) {
        return inventoryRecordMapper.selectPage(pageReqVO);
    }

}