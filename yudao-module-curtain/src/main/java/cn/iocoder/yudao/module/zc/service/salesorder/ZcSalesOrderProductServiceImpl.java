package cn.iocoder.yudao.module.zc.service.salesorder;

import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.tenant.core.context.TenantContextHolder;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.ZcCancelCutProductReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.ZcCutProductReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.ZcSalesOrderProductBatchCreateVO;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.ZcSalesOrderProductCreateReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.ZcSalesOrderProductDetailRespVO;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.ZcSalesOrderProductLineRespVO;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.ZcSalesOrderProductUpdateReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.ZcSalesOrderRespVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.inventoryrecord.ZcInventoryRecordDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.productbatch.ZcProductBatchDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZcSalesOrderDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZcSalesOrderProductDO;
import cn.iocoder.yudao.module.zc.dal.mysql.inventoryrecord.ZcInventoryRecordMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.productbatch.ZcProductBatchMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.salesorder.ZcSalesOrderMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.salesorder.ZcSalesOrderProductMapper;
import cn.iocoder.yudao.module.zc.dal.redis.ZcNoGeneratorRedisDAO;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.mzt.logapi.context.LogRecordContext;
import com.mzt.logapi.service.impl.DiffParseFunction;
import com.mzt.logapi.starter.annotation.LogRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.module.zc.enums.LogRecordConstants.*;
import cn.iocoder.yudao.module.zc.enums.ZcInventoryRecordOperateEnum;
import cn.iocoder.yudao.module.zc.enums.ZcOrderTypeEnum;
import cn.iocoder.yudao.module.zc.enums.ZcSalesOrderMaterialStatusEnum;
import cn.iocoder.yudao.module.zc.enums.ZcSalesOrderPayStatusEnum;
import cn.iocoder.yudao.module.zc.enums.ZcSalesOrderStatusEnum;

/**
 * 产品类销售订单 Service 实现类
 *
 * @author 01Coder
 */
@Service
@Validated
public class ZcSalesOrderProductServiceImpl implements ZcSalesOrderProductService {

    @Resource
    private ZcSalesOrderMapper salesOrderMapper;
    @Resource
    private ZcSalesOrderProductMapper salesOrderProductMapper;
    @Resource
    private ZcNoGeneratorRedisDAO noGeneratorRedisDAO;
    @Resource
    private ZcProductBatchMapper productBatchMapper;
    @Resource
    private ZcInventoryRecordMapper inventoryRecordMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = ZC_SALES_ORDER_PRODUCT_TYPE, subType = ZC_SALES_ORDER_PRODUCT_CREATE_SUB_TYPE, bizNo = "{{#salesOrder.id}}",
            success = ZC_SALES_ORDER_PRODUCT_CREATE_SUCCESS)
    public Long createSalesOrderProduct(ZcSalesOrderProductCreateReqVO createReqVO) {
        // 1. 生成订单号：ZC{租户ID}{yyyyMMdd}{5位序号}，Redis INCR 保证并发唯一
        Long tenantId = TenantContextHolder.getRequiredTenantId();
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        long seq = noGeneratorRedisDAO.nextOrderSeq(tenantId, date);
        String orderNo = String.format("ZC%d%s%05d", tenantId, date, seq);

        // 2. 保存订单主记录，设置自动生成/默认字段
        ZcSalesOrderDO salesOrder = BeanUtils.toBean(createReqVO, ZcSalesOrderDO.class);
        salesOrder.setOrderNo(orderNo);
        salesOrder.setTypes(ZcOrderTypeEnum.FABRIC.name()); // 产品类订单固定为面料单
        salesOrder.setPayStatus(ZcSalesOrderPayStatusEnum.UNPAID.name()); // 默认：未支付
        salesOrder.setStatus(ZcSalesOrderStatusEnum.UNCONFIRMED.name()); // 默认：未确认
        salesOrder.setIsExpedited(false);    // 默认：非加急
        salesOrder.setSets(createReqVO.getBatchs() == null ? 0 : createReqVO.getBatchs().size());
        if (salesOrder.getFreight() == null) {
            salesOrder.setFreight(java.math.BigDecimal.ZERO);
        }
        salesOrderMapper.insert(salesOrder);
        Long orderId = salesOrder.getId();

        // 3. 级联保存产品批次行
        int productIndex = 1;
        for (ZcSalesOrderProductBatchCreateVO batchVO : createReqVO.getBatchs()) {
            ZcSalesOrderProductDO productDO = BeanUtils.toBean(batchVO, ZcSalesOrderProductDO.class);
            productDO.setOrderId(orderId);
            productDO.setIndex(productIndex++);
            productDO.setStatus(ZcSalesOrderStatusEnum.UNCONFIRMED.name()); // 新建订单默认未确认
            salesOrderProductMapper.insert(productDO);
        }
        // 记录操作日志上下文
        LogRecordContext.putVariable("salesOrder", salesOrder);
        return orderId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = ZC_SALES_ORDER_PRODUCT_TYPE, subType = ZC_SALES_ORDER_PRODUCT_DELETE_SUB_TYPE, bizNo = "{{#id}}",
            success = ZC_SALES_ORDER_PRODUCT_DELETE_SUCCESS)
    public void deleteSalesOrderProduct(Long id) {
        // 1. 校验订单存在
        ZcSalesOrderDO order = validateSalesOrderExists(id);
        // 2. 已确认（confirm_time 不为空）的订单禁止删除
        if (order.getConfirmTime() != null) {
            throw exception(SALES_ORDER_CONFIRMED_CANNOT_DELETE);
        }
        // 记录操作日志上下文
        LogRecordContext.putVariable("orderNo", order.getOrderNo());
        // 3. 先删产品行，再删主记录，防止孤立数据
        salesOrderProductMapper.deleteByOrderId(id);
        salesOrderMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = ZC_SALES_ORDER_PRODUCT_TYPE, subType = ZC_SALES_ORDER_PRODUCT_UPDATE_SUB_TYPE, bizNo = "{{#updateReqVO.id}}",
            success = ZC_SALES_ORDER_PRODUCT_UPDATE_SUCCESS)
    public void updateSalesOrderProduct(ZcSalesOrderProductUpdateReqVO updateReqVO) {
        Long orderId = updateReqVO.getId();

        // 1. 校验订单存在
        ZcSalesOrderDO existing = validateSalesOrderExists(orderId);

        // 2. confirm_time 不为空表示已确认，禁止修改任何信息
        if (existing.getConfirmTime() != null) {
            throw exception(SALES_ORDER_CONFIRMED_CANNOT_UPDATE);
        }

        // 3. 更新订单主记录，保留系统字段（订单号、状态、结算状态、是否加急不允许覆盖）
        ZcSalesOrderDO updateOrder = BeanUtils.toBean(updateReqVO, ZcSalesOrderDO.class);
        updateOrder.setOrderNo(existing.getOrderNo());
        updateOrder.setTypes(existing.getTypes()); // 订单类型不允许更新，保留原值
        updateOrder.setStatus(existing.getStatus());
        updateOrder.setPayStatus(existing.getPayStatus());
        updateOrder.setIsExpedited(existing.getIsExpedited());
        updateOrder.setConfirmTime(existing.getConfirmTime());
        updateOrder.setSets(updateReqVO.getBatchs() == null ? 0 : updateReqVO.getBatchs().size());
        if (updateOrder.getFreight() == null) {
            updateOrder.setFreight(java.math.BigDecimal.ZERO);
        }
        salesOrderMapper.updateById(updateOrder);

        // 4. 整单替换产品行：先全量删除旧行，再重新插入新行
        salesOrderProductMapper.deleteByOrderId(orderId);
        int productIndex = 1;
        for (ZcSalesOrderProductBatchCreateVO batchVO : updateReqVO.getBatchs()) {
            ZcSalesOrderProductDO productDO = BeanUtils.toBean(batchVO, ZcSalesOrderProductDO.class);
            productDO.setOrderId(orderId);
            productDO.setIndex(productIndex++);
            productDO.setStatus(ZcSalesOrderStatusEnum.UNCONFIRMED.name()); // 整单更新时订单必为未确认状态
            salesOrderProductMapper.insert(productDO);
        }
        // 记录操作日志上下文（仅主表字段参与 diff）
        LogRecordContext.putVariable(DiffParseFunction.OLD_OBJECT, BeanUtils.toBean(existing, ZcSalesOrderProductUpdateReqVO.class));
        LogRecordContext.putVariable("orderNo", existing.getOrderNo());
    }

    @Override
    public ZcSalesOrderProductDetailRespVO getSalesOrderProductDetail(Long id) {
        // 1. SQL JOIN 查订单主记录（含客户名称、物流名称），不存在则抛异常
        ZcSalesOrderRespVO orderVO = salesOrderMapper.selectVOById(id);
        if (orderVO == null) {
            throw exception(SALES_ORDER_NOT_EXISTS);
        }

        // 2. SQL JOIN 查产品行（含产品名称、批次号），结果直接映射到 VO
        List<ZcSalesOrderProductLineRespVO> lines = salesOrderProductMapper.selectProductLinesWithVOByOrderId(id);

        // 3. 组装返回 VO
        ZcSalesOrderProductDetailRespVO respVO = BeanUtils.toBean(orderVO, ZcSalesOrderProductDetailRespVO.class);
        respVO.setBatchs(lines);
        return respVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = ZC_SALES_ORDER_PRODUCT_TYPE, subType = ZC_SALES_ORDER_PRODUCT_CUT_SUB_TYPE,
            bizNo = "{{#reqVO.id}}", success = ZC_SALES_ORDER_PRODUCT_CUT_SUCCESS)
    public void cutProduct(ZcCutProductReqVO reqVO) {
        // 1. 校验产品行存在，取出 batchId 和 orderId
        ZcSalesOrderProductDO productLine = validateProductLineExists(reqVO.getId());
        if (productLine.getBatchId() == null) {
            throw exception(PRODUCT_BATCH_NOT_EXISTS);
        }
        // 2. 校验批次存在且库存充足
        ZcProductBatchDO batch = productBatchMapper.selectById(productLine.getBatchId());
        if (batch == null) {
            throw exception(PRODUCT_BATCH_NOT_EXISTS);
        }
        if (batch.getQuantity().compareTo(reqVO.getCutQuantity()) < 0) {
            throw exception(PRODUCT_BATCH_INSUFFICIENT_QUANTITY);
        }
        // 3. 更新产品行：记录裁剪数量、状态变更为已配料
        salesOrderProductMapper.update(null, new LambdaUpdateWrapper<ZcSalesOrderProductDO>()
                .eq(ZcSalesOrderProductDO::getId, reqVO.getId())
                .set(ZcSalesOrderProductDO::getCutQuantity, reqVO.getCutQuantity())
                .set(ZcSalesOrderProductDO::getStatus, ZcSalesOrderStatusEnum.HAVE_PEILIAO.name()));

        // 4. 原子扣减批次剩余数量，防止并发超卖
        productBatchMapper.decreaseQuantity(productLine.getBatchId(), reqVO.getCutQuantity());
        // 5. 写入裁剪出库库存变动记录
        BigDecimal oldQuantity = batch.getQuantity();
        BigDecimal newQuantity = oldQuantity.subtract(reqVO.getCutQuantity());
        ZcInventoryRecordDO inventoryRecord = new ZcInventoryRecordDO();
        inventoryRecord.setProductId(batch.getProductId());
        inventoryRecord.setBatchId(productLine.getBatchId());
        inventoryRecord.setOldQuantity(oldQuantity);
        inventoryRecord.setNewQuantity(newQuantity);
        inventoryRecord.setChangeQuantity(newQuantity.subtract(oldQuantity));
        inventoryRecord.setOperate(ZcInventoryRecordOperateEnum.CAIJIAN.name());
        inventoryRecord.setOrderId(productLine.getOrderId());
        inventoryRecordMapper.insert(inventoryRecord);
        // 记录操作日志上下文
        LogRecordContext.putVariable("batchNo", batch.getBatchNo());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = ZC_SALES_ORDER_PRODUCT_TYPE, subType = ZC_SALES_ORDER_PRODUCT_CANCEL_CUT_SUB_TYPE,
            bizNo = "{{#reqVO.id}}", success = ZC_SALES_ORDER_PRODUCT_CANCEL_CUT_SUCCESS)
    public void cancelCutProduct(ZcCancelCutProductReqVO reqVO) {
        // 1. 校验产品行存在
        ZcSalesOrderProductDO productLine = validateProductLineExists(reqVO.getId());
        // 2. 只有已配料的产品行才能撤销
        if (!ZcSalesOrderStatusEnum.HAVE_PEILIAO.name().equals(productLine.getStatus())) {
            throw exception(SALES_ORDER_PRODUCT_NOT_PEILIAO);
        }
        // 3. 校验批次存在
        ZcProductBatchDO batch = productBatchMapper.selectById(productLine.getBatchId());
        if (batch == null) {
            throw exception(PRODUCT_BATCH_NOT_EXISTS);
        }
        // 4. 原子回退批次库存
        productBatchMapper.increaseQuantity(productLine.getBatchId(), productLine.getCutQuantity());
        // 5. 重置产品行：清空裁剪数量，状态回退为未配料
        // 用 LambdaUpdateWrapper 显式将 cutQuantity 置为 null（updateById 会忽略 null 字段）
        salesOrderProductMapper.update(null, new LambdaUpdateWrapper<ZcSalesOrderProductDO>()
                .eq(ZcSalesOrderProductDO::getId, reqVO.getId())
                .set(ZcSalesOrderProductDO::getStatus, ZcSalesOrderStatusEnum.NOT_PEILIAO.name())
                .set(ZcSalesOrderProductDO::getCutQuantity, null));
        // 6. 写入撤销裁剪库存变动记录
        BigDecimal oldQuantity = batch.getQuantity();
        BigDecimal newQuantity = oldQuantity.add(productLine.getCutQuantity());
        ZcInventoryRecordDO inventoryRecord = new ZcInventoryRecordDO();
        inventoryRecord.setProductId(batch.getProductId());
        inventoryRecord.setBatchId(productLine.getBatchId());
        inventoryRecord.setOldQuantity(oldQuantity);
        inventoryRecord.setNewQuantity(newQuantity);
        inventoryRecord.setChangeQuantity(newQuantity.subtract(oldQuantity));
        inventoryRecord.setOperate(ZcInventoryRecordOperateEnum.CANCEL_CAIJIAN.name());
        inventoryRecord.setOrderId(productLine.getOrderId());
        inventoryRecordMapper.insert(inventoryRecord);
        // 记录操作日志上下文
        LogRecordContext.putVariable("batchNo", batch.getBatchNo());
        LogRecordContext.putVariable("cutQuantity", productLine.getCutQuantity());
    }

    private ZcSalesOrderDO validateSalesOrderExists(Long id) {
        ZcSalesOrderDO order = salesOrderMapper.selectById(id);
        if (order == null) {
            throw exception(SALES_ORDER_NOT_EXISTS);
        }
        return order;
    }

    private ZcSalesOrderProductDO validateProductLineExists(Long id) {
        ZcSalesOrderProductDO productLine = salesOrderProductMapper.selectById(id);
        if (productLine == null) {
            throw exception(SALES_ORDER_PRODUCT_NOT_EXISTS);
        }
        return productLine;
    }

}
