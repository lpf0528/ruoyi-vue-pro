package cn.iocoder.yudao.module.zc.service.stock;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.stock.ZcInventoryRecordDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.stock.ZcProductBatchDO;
import cn.iocoder.yudao.module.zc.dal.mysql.stock.ZcInventoryRecordMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.stock.ZcProductBatchMapper;
import cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants;
import cn.iocoder.yudao.module.zc.controller.admin.vo.stock.ZcInventoryRecordPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.stock.ZcInventoryRecordSaveReqVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service
@Validated
public class ZcInventoryRecordServiceImpl implements ZcInventoryRecordService {

    @Resource
    private ZcInventoryRecordMapper inventoryRecordMapper;
    @Resource
    private ZcProductBatchMapper productBatchMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(ZcInventoryRecordSaveReqVO reqVO) {
        ZcProductBatchDO batch = productBatchMapper.selectById(reqVO.getBatchId());
        if (batch == null) {
            throw exception(ErrorCodeConstants.PRODUCT_BATCH_NOT_EXISTS);
        }
        if (!batch.getProductId().equals(reqVO.getProductId())) {
            throw exception(ErrorCodeConstants.PRODUCT_BATCH_NOT_EXISTS);
        }
        BigDecimal oldQty = batch.getQuantity() != null ? batch.getQuantity() : BigDecimal.ZERO;

        ZcInventoryRecordDO rec = new ZcInventoryRecordDO();
        rec.setProductId(reqVO.getProductId());
        rec.setBatchId(reqVO.getBatchId());
        rec.setOldQuantity(oldQty);
        rec.setNewQuantity(reqVO.getNewQuantity());
        rec.setNote(reqVO.getNote());
        inventoryRecordMapper.insert(rec);

        ZcProductBatchDO u = new ZcProductBatchDO();
        u.setId(batch.getId());
        u.setQuantity(reqVO.getNewQuantity());
        productBatchMapper.updateById(u);

        return rec.getId();
    }

    @Override
    public ZcInventoryRecordDO get(Long id) {
        return inventoryRecordMapper.selectById(id);
    }

    @Override
    public PageResult<ZcInventoryRecordDO> getPage(ZcInventoryRecordPageReqVO pageReqVO) {
        return inventoryRecordMapper.selectPage(pageReqVO, new LambdaQueryWrapperX<ZcInventoryRecordDO>()
                .eqIfPresent(ZcInventoryRecordDO::getProductId, pageReqVO.getProductId())
                .eqIfPresent(ZcInventoryRecordDO::getBatchId, pageReqVO.getBatchId())
                .orderByDesc(ZcInventoryRecordDO::getId));
    }

}
