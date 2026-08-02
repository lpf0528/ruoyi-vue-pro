package cn.iocoder.yudao.module.zc.service.salesorder;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.processnode.ZcOrderProcessRecordDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.processnode.ZcProcessNodeDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.productbatch.ZcProductBatchDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZCSalesOrderMaterialDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZcSalesOrderDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.inventoryrecord.ZcInventoryRecordDO;
import cn.iocoder.yudao.module.zc.dal.mysql.inventoryrecord.ZcInventoryRecordMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.processnode.ZcOrderProcessRecordMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.productbatch.ZcProductBatchMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.salesorder.ZCSalesOrderMaterialMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.salesorder.ZcSalesOrderMapper;
import cn.iocoder.yudao.module.zc.dal.dataobject.workshopuser.ZcWorkshopUserDO;
import cn.iocoder.yudao.module.zc.enums.ZcSystemProcessNodeEnum;
import cn.iocoder.yudao.module.zc.service.processnode.ZcOrderProcessRecordScopeHelper;
import cn.iocoder.yudao.module.zc.service.processnode.ZcSystemProcessNodeHelper;
import cn.iocoder.yudao.module.zc.service.workshopuser.ZcWorkshopUserService;
import cn.iocoder.yudao.module.zc.enums.ZcInventoryRecordOperateEnum;
import cn.iocoder.yudao.module.zc.enums.ZcProductBatchStatusEnum;
import cn.iocoder.yudao.module.zc.enums.ZcSalesOrderMaterialStatusEnum;
import com.mzt.logapi.context.LogRecordContext;
import com.mzt.logapi.starter.annotation.LogRecord;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.module.zc.enums.LogRecordConstants.*;

/**
 * 成品订单-用料明细 Service 实现类
 *
 * @author 01Coder
 */
@Service
@Validated
public class ZCSalesOrderMaterialServiceImpl implements ZCSalesOrderMaterialService {

    @Resource
    private ZCSalesOrderMaterialMapper zCSalesOrderMaterialMapper;
    @Resource
    private ZcProductBatchMapper productBatchMapper;
    @Resource
    private ZcInventoryRecordMapper inventoryRecordMapper;
    @Resource
    private ZcOrderProcessRecordMapper processRecordMapper;
    @Resource
    private ZcSalesOrderMapper salesOrderMapper;
    @Resource
    private ZcWorkshopUserService workshopUserService;
    @Resource
    private ZcSalesOrderStatusCalculator orderStatusCalculator;
    @Resource
    private ZcOrderProcessRecordScopeHelper processRecordScopeHelper;
    @Resource
    private ZcSystemProcessNodeHelper systemProcessNodeHelper;

    private ZCSalesOrderMaterialDO validateZCSalesOrderMaterialExists(Long id) {
        ZCSalesOrderMaterialDO material = zCSalesOrderMaterialMapper.selectById(id);
        if (material == null) {
            throw exception(ZC_SALES_ORDER_MATERIAL_NOT_EXISTS);
        }
        return material;
    }

    @Override
    public ZCSalesOrderMaterialDO getZCSalesOrderMaterial(Long id) {
        return zCSalesOrderMaterialMapper.selectById(id);
    }

    @Override
    public ZCSalesOrderMaterialPageRespVO getZCSalesOrderMaterialPage(ZCSalesOrderMaterialPageReqVO pageReqVO) {
        return zCSalesOrderMaterialMapper.selectPage(pageReqVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = ZC_SALES_ORDER_MATERIAL_TYPE, subType = ZC_SALES_ORDER_MATERIAL_CUT_SUB_TYPE,
            bizNo = "{{#reqVO.id}}", success = ZC_SALES_ORDER_MATERIAL_CUT_SUCCESS)
    public void cutMaterial(ZcCutMaterialReqVO reqVO) {
        // 校验用料明细存在，取出 orderId 供库存记录关联
        ZCSalesOrderMaterialDO material = validateZCSalesOrderMaterialExists(reqVO.getId());
        // 已裁剪的用料不允许重复裁剪，须先撤销
        if (ZcSalesOrderMaterialStatusEnum.HAVE_PEILIAO.name().equals(material.getStatus())) {
            throw exception(SALES_ORDER_MATERIAL_ALREADY_CUT);
        }
        // 校验批次存在且库存充足
        ZcProductBatchDO batch = productBatchMapper.selectById(reqVO.getBatchId());
        if (batch == null) {
            throw exception(PRODUCT_BATCH_NOT_EXISTS);
        }
        if (batch.getQuantity().compareTo(reqVO.getCutQuantity()) < 0) {
            throw exception(PRODUCT_BATCH_INSUFFICIENT_QUANTITY);
        }
        // 更新用料明细：绑定批次、记录裁剪数量、状态变更为已配料
        // 不可使用 updateById：elementId/spec 等字段为 FieldStrategy.ALWAYS，部分更新会把未赋值字段写成 null
        zCSalesOrderMaterialMapper.update(null, new LambdaUpdateWrapper<ZCSalesOrderMaterialDO>()
                .eq(ZCSalesOrderMaterialDO::getId, reqVO.getId())
                .set(ZCSalesOrderMaterialDO::getBatchId, reqVO.getBatchId())
                .set(ZCSalesOrderMaterialDO::getCutQuantity, reqVO.getCutQuantity())
                .set(ZCSalesOrderMaterialDO::getStatus, ZcSalesOrderMaterialStatusEnum.HAVE_PEILIAO.name()));
        // 原子扣减批次剩余数量，防止并发超卖
        productBatchMapper.decreaseQuantity(reqVO.getBatchId(), reqVO.getCutQuantity());
        // 整匹批次裁剪后调整为余料
        if (ZcProductBatchStatusEnum.WHOLE.getStatus().equals(batch.getStatus())) {
            productBatchMapper.update(null, Wrappers.<ZcProductBatchDO>lambdaUpdate()
                    .set(ZcProductBatchDO::getStatus, ZcProductBatchStatusEnum.SURPLUS.getStatus())
                    .eq(ZcProductBatchDO::getId, reqVO.getBatchId()));
        }

        // 裁剪出库：写入库存变动记录
        java.math.BigDecimal oldQuantity = batch.getQuantity();
        java.math.BigDecimal newQuantity = oldQuantity.subtract(reqVO.getCutQuantity());
        ZcInventoryRecordDO inventoryRecord = new ZcInventoryRecordDO();
        inventoryRecord.setProductId(batch.getProductId());
        inventoryRecord.setBatchId(reqVO.getBatchId());
        inventoryRecord.setOldQuantity(oldQuantity);
        inventoryRecord.setNewQuantity(newQuantity);
        inventoryRecord.setChangeQuantity(newQuantity.subtract(oldQuantity));
        inventoryRecord.setOperate(ZcInventoryRecordOperateEnum.CAIJIAN.name());
        inventoryRecord.setOrderId(material.getOrderId());
        inventoryRecordMapper.insert(inventoryRecord);

        // 查找系统配置的"配料"工序节点（group=0），有则自动创建工序完成记录
        createPeiliaoProcessRecord(material, reqVO.getMasterId(), reqVO.getAssistantId());

        // 联动更新窗帘行配料状态 → 订单主表状态
        orderStatusCalculator.syncAfterMaterialChange(material.getOrderId(), material.getOrderStructureId());

        // 记录操作日志上下文
        LogRecordContext.putVariable("batchNo", batch.getBatchNo());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = ZC_SALES_ORDER_MATERIAL_TYPE, subType = ZC_SALES_ORDER_MATERIAL_CANCEL_CUT_SUB_TYPE,
            bizNo = "{{#reqVO.materialId}}", success = ZC_SALES_ORDER_MATERIAL_CANCEL_CUT_SUCCESS)
    public void cancelCutMaterial(ZcCancelCutMaterialReqVO reqVO) {
        Long materialId = reqVO.getMaterialId();
        // 1. 校验用料明细存在
        ZCSalesOrderMaterialDO material = validateZCSalesOrderMaterialExists(materialId);
        // 2. 只有已配料的明细才能撤销
        if (!ZcSalesOrderMaterialStatusEnum.HAVE_PEILIAO.name().equals(material.getStatus())) {
            throw exception(SALES_ORDER_MATERIAL_NOT_PEILIAO);
        }
        // 3. 校验批次存在
        ZcProductBatchDO batch = productBatchMapper.selectById(material.getBatchId());
        if (batch == null) {
            throw exception(PRODUCT_BATCH_NOT_EXISTS);
        }

        // 4. 原子回退批次库存
        productBatchMapper.increaseQuantity(material.getBatchId(), material.getCutQuantity());

        // 5. 重置用料明细：清空裁剪数量，状态回退为未配料（batchId 保留，方便重新裁剪时复用）
        // 用 LambdaUpdateWrapper 显式将 cutQuantity 置为 null（updateById 会忽略 null 字段）
        zCSalesOrderMaterialMapper.update(null, new LambdaUpdateWrapper<ZCSalesOrderMaterialDO>()
                .eq(ZCSalesOrderMaterialDO::getId, materialId)
                .set(ZCSalesOrderMaterialDO::getStatus, ZcSalesOrderMaterialStatusEnum.NOT_PEILIAO.name())
                .set(ZCSalesOrderMaterialDO::getCutQuantity, null));

        // 6. 写入撤销裁剪库存变动记录
        java.math.BigDecimal oldQuantity = batch.getQuantity();
        java.math.BigDecimal newQuantity = oldQuantity.add(material.getCutQuantity());
        ZcInventoryRecordDO inventoryRecord = new ZcInventoryRecordDO();
        inventoryRecord.setProductId(batch.getProductId());
        inventoryRecord.setBatchId(material.getBatchId());
        inventoryRecord.setOldQuantity(oldQuantity);
        inventoryRecord.setNewQuantity(newQuantity);
        inventoryRecord.setChangeQuantity(newQuantity.subtract(oldQuantity));
        inventoryRecord.setOperate(ZcInventoryRecordOperateEnum.CANCEL_CAIJIAN.name());
        inventoryRecord.setOrderId(material.getOrderId());
        inventoryRecordMapper.insert(inventoryRecord);

        // 撤销该用料明细对应的"配料"工序完成记录（若存在），写入操作人信息
        revokePeiliaoProcessRecord(material, reqVO.getMasterId(), reqVO.getAssistantId());

        // 联动更新窗帘行配料状态 → 订单主表状态
        orderStatusCalculator.syncAfterMaterialChange(material.getOrderId(), material.getOrderStructureId());

        // 记录操作日志上下文
        LogRecordContext.putVariable("batchNo", batch.getBatchNo());
        LogRecordContext.putVariable("cutQuantity", material.getCutQuantity());
    }

    /**
     * 在裁剪完成后，自动创建"配料"工序完成记录
     *
     * <p>仅处理系统配置（group=0）且名称为"配料"的工序节点；
     * 用料级记录会补齐 curtainId、structureId，保证定位链完整。</p>
     *
     * @param material    用料明细
     * @param masterId    主操作人员 ID
     * @param assistantId 副操作人员 ID，可为空
     */
    private void createPeiliaoProcessRecord(ZCSalesOrderMaterialDO material,
                                            Long masterId, Long assistantId) {
        ZcSystemProcessNodeEnum nodeEnum = ZcSystemProcessNodeEnum.PEILIAO;
        ZcProcessNodeDO node = systemProcessNodeHelper.getSystemNode(nodeEnum);
        String nodeName = systemProcessNodeHelper.resolveNodeName(node, nodeEnum);
        ZcOrderProcessRecordScopeHelper.Scope scope = processRecordScopeHelper.normalize(
                material.getOrderId(), null, material.getOrderStructureId(), material.getId());
        processRecordMapper.insert(ZcOrderProcessRecordDO.builder()
                .orderId(scope.getOrderId())
                .curtainId(scope.getCurtainId())
                .structureId(scope.getStructureId())
                .materialId(scope.getMaterialId())
                .nodeId(systemProcessNodeHelper.resolveNodeId(node))
                .nodeName(nodeName)
                .status(1)
                .masterId(masterId)
                .assistantId(assistantId)
                .build());
        // 同步更新订单当前工序名称快照
        salesOrderMapper.update(null, Wrappers.<ZcSalesOrderDO>lambdaUpdate()
                .set(ZcSalesOrderDO::getCurrentNodeName, nodeName)
                .eq(ZcSalesOrderDO::getId, scope.getOrderId()));
    }

    /**
     * 在撤销裁剪后，将对应"配料"工序记录状态改为撤销（status=2），并写入操作人信息至 note
     *
     * <p>仅处理系统配置（group=0）且名称为"配料"的工序节点；
     * 若找不到对应完成记录则静默跳过。</p>
     *
     * @param material    用料明细
     * @param masterId    主操作人员 ID
     * @param assistantId 副操作人员 ID，可为空
     */
    private void revokePeiliaoProcessRecord(ZCSalesOrderMaterialDO material,
                                            Long masterId, Long assistantId) {
        ZcSystemProcessNodeEnum nodeEnum = ZcSystemProcessNodeEnum.PEILIAO;
        ZcProcessNodeDO node = systemProcessNodeHelper.getSystemNode(nodeEnum);
        ZcOrderProcessRecordScopeHelper.Scope scope = processRecordScopeHelper.normalize(
                material.getOrderId(), null, material.getOrderStructureId(), material.getId());
        ZcOrderProcessRecordDO record = processRecordMapper.selectCompletedRecord(
                scope.getOrderId(), scope.getCurtainId(), scope.getStructureId(),
                scope.getMaterialId(), node.getId());
        if (record == null) {
            return;
        }
        // 查询操作员姓名，构建撤销备注
        ZcWorkshopUserDO masterUser = workshopUserService.getWorkshopUser(masterId);
        String cancelNote = "撤销人：" + (masterUser != null ? masterUser.getName() : masterId);
        if (assistantId != null) {
            ZcWorkshopUserDO assistantUser = workshopUserService.getWorkshopUser(assistantId);
            cancelNote += "，副操作人：" + (assistantUser != null ? assistantUser.getName() : assistantId);
        }
        processRecordMapper.update(null, Wrappers.<ZcOrderProcessRecordDO>lambdaUpdate()
                .set(ZcOrderProcessRecordDO::getStatus, 2)
                .set(ZcOrderProcessRecordDO::getNote, cancelNote)
                .eq(ZcOrderProcessRecordDO::getId, record.getId()));
    }

}
