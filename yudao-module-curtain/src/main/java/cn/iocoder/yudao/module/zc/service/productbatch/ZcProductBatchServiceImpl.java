package cn.iocoder.yudao.module.zc.service.productbatch;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import cn.iocoder.yudao.module.zc.controller.admin.inventoryrecord.vo.ZcInventoryRecordSaveReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.productbatch.vo.*;
import cn.iocoder.yudao.module.zc.service.inventoryrecord.ZcInventoryRecordService;
import cn.iocoder.yudao.module.zc.dal.dataobject.productbatch.ZcProductBatchDO;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.zc.dal.dataobject.inventoryrecord.ZcInventoryRecordDO;
import cn.iocoder.yudao.module.zc.dal.mysql.inventoryrecord.ZcInventoryRecordMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.productbatch.ZcProductBatchMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.salesorder.ZCSalesOrderMaterialMapper;
import cn.iocoder.yudao.module.zc.dal.redis.ZcBarcodeGeneratorRedisDAO;
import cn.iocoder.yudao.module.zc.dal.redis.ZcNoGeneratorRedisDAO;
import cn.iocoder.yudao.module.zc.enums.ZcInventoryRecordOperateEnum;
import cn.iocoder.yudao.framework.tenant.core.context.TenantContextHolder;
import com.mzt.logapi.context.LogRecordContext;
import com.mzt.logapi.service.impl.DiffParseFunction;
import com.mzt.logapi.starter.annotation.LogRecord;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.module.zc.enums.LogRecordConstants.*;

/**
 * 产品批次 Service 实现类
 *
 * @author 01Coder
 */
@Service
@Validated
public class ZcProductBatchServiceImpl implements ZcProductBatchService {

    @Resource
    private ZcProductBatchMapper productBatchMapper;
    @Resource
    private ZCSalesOrderMaterialMapper salesOrderMaterialMapper;
    @Resource
    private ZcInventoryRecordMapper inventoryRecordMapper;
    @Resource
    private ZcNoGeneratorRedisDAO noGeneratorRedisDAO;
    @Resource
    private ZcBarcodeGeneratorRedisDAO barcodeGeneratorRedisDAO;
    @Resource
    private ZcInventoryRecordService inventoryRecordService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = ZC_PRODUCT_BATCH_TYPE, subType = ZC_PRODUCT_BATCH_CREATE_SUB_TYPE, bizNo = "{{#productBatch.id}}",
            success = ZC_PRODUCT_BATCH_CREATE_SUCCESS)
    public Long createProductBatch(ZcProductBatchSaveReqVO createReqVO) {
        ZcProductBatchDO productBatch = BeanUtils.toBean(createReqVO, ZcProductBatchDO.class);
        // 生成批号：{yyyyMMdd}-{2位序号}，Redis INCR 保证并发唯一（按产品隔离，跨日从 01 重置）
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        long seq = noGeneratorRedisDAO.nextBatchSeq(
                TenantContextHolder.getRequiredTenantId(), createReqVO.getProductId(), date);
        productBatch.setBatchNo(date + "-" + String.format("%02d", seq));
        // 生成条码：BAT-XXXXXXXX（12位），Redis setIfAbsent 保证全局唯一
        productBatch.setBarcode(barcodeGeneratorRedisDAO.generateBatchBarcode());
        productBatchMapper.insert(productBatch);

        // 入库时写入库存变动记录：old=0，new=入库数量，operate=RUKU
        ZcInventoryRecordDO inventoryRecord = new ZcInventoryRecordDO();
        inventoryRecord.setProductId(createReqVO.getProductId());
        inventoryRecord.setBatchId(productBatch.getId());
        inventoryRecord.setOldQuantity(java.math.BigDecimal.ZERO);
        inventoryRecord.setNewQuantity(createReqVO.getQuantity());
        inventoryRecord.setChangeQuantity(createReqVO.getQuantity());
        inventoryRecord.setOperate(ZcInventoryRecordOperateEnum.RUKU.name());
        inventoryRecordMapper.insert(inventoryRecord);

        // 记录操作日志上下文
        LogRecordContext.putVariable("productBatch", productBatch);
        return productBatch.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<ZcProductBatchRespVO> createProductBatchList(List<ZcProductBatchSaveReqVO> createReqVOs) {
        if (createReqVOs == null || createReqVOs.isEmpty()) {
            return Collections.emptyList();
        }
        List<Long> ids = new ArrayList<>(createReqVOs.size());
        for (ZcProductBatchSaveReqVO createReqVO : createReqVOs) {
            ids.add(createProductBatch(createReqVO));
        }
        ZcProductBatchPageReqVO pageReqVO = new ZcProductBatchPageReqVO();
        pageReqVO.setIds(ids);
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        return productBatchMapper.selectPage(pageReqVO).getList();
    }

    @Override
    @LogRecord(type = ZC_PRODUCT_BATCH_TYPE, subType = ZC_PRODUCT_BATCH_UPDATE_SUB_TYPE, bizNo = "{{#updateReqVO.id}}",
            success = ZC_PRODUCT_BATCH_UPDATE_SUCCESS)
    public void updateProductBatch(ZcProductBatchSaveReqVO updateReqVO) {
        // 校验存在
        ZcProductBatchDO oldProductBatch = validateProductBatchExists(updateReqVO.getId());
        // 更新
        ZcProductBatchDO updateObj = BeanUtils.toBean(updateReqVO, ZcProductBatchDO.class);
        productBatchMapper.updateById(updateObj);
        // 记录操作日志上下文
        LogRecordContext.putVariable(DiffParseFunction.OLD_OBJECT, BeanUtils.toBean(oldProductBatch, ZcProductBatchSaveReqVO.class));
        LogRecordContext.putVariable("batchNo", oldProductBatch.getBatchNo());
    }

    @Override
    public void updateProductBatchStatus(ZcProductBatchUpdateStatusReqVO updateReqVO) {
        // 校验存在
        validateProductBatchExists(updateReqVO.getId());
        // 更新
        ZcProductBatchDO updateObj = new ZcProductBatchDO();
        updateObj.setId(updateReqVO.getId());
        updateObj.setStatus(updateReqVO.getStatus());
        productBatchMapper.updateById(updateObj);
    }

    @Override
    @LogRecord(type = ZC_PRODUCT_BATCH_TYPE, subType = ZC_PRODUCT_BATCH_DELETE_SUB_TYPE, bizNo = "{{#id}}",
            success = ZC_PRODUCT_BATCH_DELETE_SUCCESS)
    public void deleteProductBatch(Long id) {
        // 校验存在
        ZcProductBatchDO productBatch = validateProductBatchExists(id);
        // 校验该批次是否已被订单用料明细引用，有则禁止删除
        if (salesOrderMaterialMapper.countByBatchId(id) > 0) {
            throw exception(PRODUCT_BATCH_HAS_ORDER_MATERIALS);
        }
        // 记录操作日志上下文
        LogRecordContext.putVariable("batchNo", productBatch.getBatchNo());
        // 删除
        productBatchMapper.deleteById(id);
    }

    @Override
    public void deleteProductBatchListByIds(List<Long> ids) {
        // 校验每个批次是否已被订单用料明细引用
        ids.forEach(id -> {
            if (salesOrderMaterialMapper.countByBatchId(id) > 0) {
                throw exception(PRODUCT_BATCH_HAS_ORDER_MATERIALS);
            }
        });
        // 删除
        productBatchMapper.deleteByIds(ids);
    }


    private ZcProductBatchDO validateProductBatchExists(Long id) {
        ZcProductBatchDO productBatch = productBatchMapper.selectById(id);
        if (productBatch == null) {
            throw exception(PRODUCT_BATCH_NOT_EXISTS);
        }
        return productBatch;
    }

    @Override
    public ZcProductBatchRespVO getProductBatch(Long id) {
        return productBatchMapper.selectBatchWithVOById(id);
    }

    @Override
    public PageResult<ZcProductBatchRespVO> getProductBatchPage(ZcProductBatchPageReqVO pageReqVO) {
        return productBatchMapper.selectPage(pageReqVO);
    }

    @Override
    public Long inventoryProductBatch(ZcInventoryRecordSaveReqVO inventoryReqVO) {
        return inventoryRecordService.createInventoryRecord(inventoryReqVO);
    }

}
