package cn.iocoder.yudao.module.zc.service.salesorder;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mzt.logapi.context.LogRecordContext;
import com.mzt.logapi.starter.annotation.LogRecord;

import cn.iocoder.yudao.module.zc.dal.dataobject.orderoperationlog.ZcOrderOperationLogDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.processnode.ZcOrderProcessRecordDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.processnode.ZcProcessNodeDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZcSalesOrderCurtainDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZcSalesOrderDO;
import cn.iocoder.yudao.module.zc.dal.mysql.processnode.ZcOrderProcessRecordMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.salesorder.ZcSalesOrderCurtainMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.salesorder.ZcSalesOrderMapper;
import cn.iocoder.yudao.module.zc.enums.ZcOrderOperateTargetTypeEnum;
import cn.iocoder.yudao.module.zc.enums.ZcOrderOperateTypeEnum;
import cn.iocoder.yudao.module.zc.enums.ZcSalesOrderStatusEnum;
import cn.iocoder.yudao.module.zc.enums.ZcSystemProcessNodeEnum;
import cn.iocoder.yudao.module.zc.dal.dataobject.workshopuser.ZcWorkshopUserDO;
import cn.iocoder.yudao.module.zc.service.orderoperationlog.ZcOrderOperationLogService;
import cn.iocoder.yudao.module.zc.service.processnode.ZcOrderProcessRecordScopeHelper;
import cn.iocoder.yudao.module.zc.service.processnode.ZcSystemProcessNodeHelper;
import cn.iocoder.yudao.module.zc.service.workshopuser.ZcWorkshopUserService;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.module.zc.enums.LogRecordConstants.*;

/**
 * 成品订单-窗帘行 Service 实现类
 *
 * @author o1Coder
 */
@Service
@Validated
public class ZcSalesOrderCurtainServiceImpl implements ZcSalesOrderCurtainService {

    @Resource
    private ZcSalesOrderCurtainMapper salesOrderCurtainMapper;
    @Resource
    private ZcSalesOrderMapper salesOrderMapper;
    @Resource
    private ZcOrderOperationLogService orderOperationLogService;
    @Resource
    private ZcOrderProcessRecordMapper processRecordMapper;
    @Resource
    private ZcWorkshopUserService workshopUserService;
    @Resource
    private ZcSalesOrderStatusCalculator orderStatusCalculator;
    @Resource
    private ZcOrderProcessRecordScopeHelper processRecordScopeHelper;
    @Resource
    private ZcSystemProcessNodeHelper systemProcessNodeHelper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = ZC_SALES_ORDER_CURTAIN_TYPE, subType = ZC_SALES_ORDER_CURTAIN_PACK_SUB_TYPE,
            bizNo = "{{#id}}", success = ZC_SALES_ORDER_CURTAIN_PACK_SUCCESS)
    public void packCurtain(Long id, Long masterId, Long assistantId) {
        // 1. 校验窗帘行存在，记录操作前状态
        ZcSalesOrderCurtainDO curtain = validateSalesOrderCurtainExists(id);
        Long orderId = curtain.getOrderId();
        String beforeStatus = curtain.getStatus();

        // 打包时间不为空说明已打包，防止重复打包
        if (curtain.getPackTime() != null) {
            throw exception(SALES_ORDER_CURTAIN_ALREADY_PACKED);
        }

        // 2. 更新窗帘行状态为已打包，同步记录打包时间
        ZcSalesOrderCurtainDO updateObj = new ZcSalesOrderCurtainDO();
        updateObj.setId(id);
        updateObj.setStatus(ZcSalesOrderStatusEnum.DABAO.name());
        updateObj.setPackTime(LocalDateTime.now());
        salesOrderCurtainMapper.updateById(updateObj);

        // 3. 联动更新订单主表状态（由窗帘行聚合）
        ZcSalesOrderDO order = salesOrderMapper.selectById(orderId);
        String newOrderStatus = orderStatusCalculator.syncOrderStatus(orderId);

        // 4. 写入操作记录
        orderOperationLogService.createLog(ZcOrderOperationLogDO.builder()
                .orderId(orderId)
                .orderNo(order != null ? order.getOrderNo() : "")
                .operateType(ZcOrderOperateTypeEnum.PACK.name())
                .targetType(ZcOrderOperateTargetTypeEnum.CURTAIN.name())
                .targetId(id)
                .beforeStatus(beforeStatus)
                .afterStatus(ZcSalesOrderStatusEnum.DABAO.name())
                .orderAfterStatus(newOrderStatus)
                .build());

        // 5. 查询系统配置的打包工序节点，写入已完成的工序流水记录
        //    节点不存在时 nodeId 置 null，nodeName 从字典兜底
        ZcProcessNodeDO packNode = systemProcessNodeHelper.getSystemNode(ZcSystemProcessNodeEnum.PACK);
        insertCurtainProcessRecord(orderId, id, packNode, ZcSystemProcessNodeEnum.PACK, masterId, assistantId);

        LogRecordContext.putVariable("newOrderStatus",
                ZcSalesOrderStatusEnum.valueOf(newOrderStatus).getLabel());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = ZC_SALES_ORDER_CURTAIN_TYPE, subType = ZC_SALES_ORDER_CURTAIN_SHIP_SUB_TYPE,
            bizNo = "{{#id}}", success = ZC_SALES_ORDER_CURTAIN_SHIP_SUCCESS)
    public void shipCurtain(Long id, Long masterId, Long assistantId) {
        // 1. 校验窗帘行存在，记录操作前状态
        ZcSalesOrderCurtainDO curtain = validateSalesOrderCurtainExists(id);
        Long orderId = curtain.getOrderId();
        String beforeStatus = curtain.getStatus();

        // 发货时间不为空说明已发货，防止重复发货
        if (curtain.getShipTime() != null) {
            throw exception(SALES_ORDER_CURTAIN_ALREADY_SHIPPED);
        }

        // 2. 更新窗帘行状态为已发货，同步记录发货时间
        ZcSalesOrderCurtainDO updateObj = new ZcSalesOrderCurtainDO();
        updateObj.setId(id);
        updateObj.setStatus(ZcSalesOrderStatusEnum.FAHUO.name());
        updateObj.setShipTime(LocalDateTime.now());
        salesOrderCurtainMapper.updateById(updateObj);

        // 3. 联动更新订单主表状态（由窗帘行聚合）
        ZcSalesOrderDO order = salesOrderMapper.selectById(orderId);
        String newOrderStatus = orderStatusCalculator.syncOrderStatus(orderId);

        // 4. 写入操作记录
        orderOperationLogService.createLog(ZcOrderOperationLogDO.builder()
                .orderId(orderId)
                .orderNo(order != null ? order.getOrderNo() : "")
                .operateType(ZcOrderOperateTypeEnum.SHIP.name())
                .targetType(ZcOrderOperateTargetTypeEnum.CURTAIN.name())
                .targetId(id)
                .beforeStatus(beforeStatus)
                .afterStatus(ZcSalesOrderStatusEnum.FAHUO.name())
                .orderAfterStatus(newOrderStatus)
                .build());

        // 5. 查询系统配置的发货工序节点，写入已完成的工序流水记录
        //    节点不存在时 nodeId 置 null，nodeName 从字典兜底
        ZcProcessNodeDO shipNode = systemProcessNodeHelper.getSystemNode(ZcSystemProcessNodeEnum.SHIP);
        insertCurtainProcessRecord(orderId, id, shipNode, ZcSystemProcessNodeEnum.SHIP, masterId, assistantId);

        LogRecordContext.putVariable("newOrderStatus",
                ZcSalesOrderStatusEnum.valueOf(newOrderStatus).getLabel());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = ZC_SALES_ORDER_CURTAIN_TYPE, subType = ZC_SALES_ORDER_CURTAIN_CANCEL_PACK_SUB_TYPE,
            bizNo = "{{#id}}", success = ZC_SALES_ORDER_CURTAIN_CANCEL_PACK_SUCCESS)
    public void cancelPackCurtain(Long id, Long masterId, Long assistantId, String reason) {
        // 1. 校验窗帘行存在，记录操作前状态
        ZcSalesOrderCurtainDO curtain = validateSalesOrderCurtainExists(id);
        Long orderId = curtain.getOrderId();
        String beforeStatus = curtain.getStatus();

        // 打包时间为空说明尚未打包，无需撤销
        if (curtain.getPackTime() == null) {
            throw exception(SALES_ORDER_CURTAIN_NOT_PACKED);
        }
        // 窗帘行已发货，不允许回退打包状态
        if (ZcSalesOrderStatusEnum.FAHUO.name().equals(curtain.getStatus())) {
            throw exception(SALES_ORDER_CURTAIN_ALREADY_SHIPPED);
        }

        // 2. 回退窗帘行配料状态（按下属用料重算），清空打包时间
        String restoredStatus = orderStatusCalculator.calculateCurtainPeiliaoStatusByCurtainId(id);
        salesOrderCurtainMapper.update(null, Wrappers.<ZcSalesOrderCurtainDO>lambdaUpdate()
                .set(ZcSalesOrderCurtainDO::getStatus, restoredStatus)
                .set(ZcSalesOrderCurtainDO::getPackTime, null)
                .eq(ZcSalesOrderCurtainDO::getId, id));

        // 3. 联动更新订单主表状态
        String newOrderStatus = orderStatusCalculator.syncOrderStatus(orderId);

        // 4. 查询操作员姓名，构建撤销备注（格式：取消的操作员是：{姓名}，原因：{reason}）
        ZcWorkshopUserDO masterUser = workshopUserService.getWorkshopUser(masterId);
        String cancelNote = "取消的操作员是：" + (masterUser != null ? masterUser.getName() : masterId)
                + (reason != null && !reason.isEmpty() ? "，原因：" + reason : "");

        // 5. 查询系统配置的打包工序节点，将该窗帘行已完成的打包工序记录撤销（status 改为 2），并写入备注
        ZcProcessNodeDO packNode = systemProcessNodeHelper.getSystemNode(ZcSystemProcessNodeEnum.PACK);
        if (packNode != null) {
            processRecordMapper.update(null, Wrappers.<ZcOrderProcessRecordDO>lambdaUpdate()
                    .set(ZcOrderProcessRecordDO::getStatus, 2)
                    .set(ZcOrderProcessRecordDO::getNote, cancelNote)
                    .eq(ZcOrderProcessRecordDO::getOrderId, orderId)
                    .eq(ZcOrderProcessRecordDO::getCurtainId, id)
                    .eq(ZcOrderProcessRecordDO::getNodeId, packNode.getId())
                    .eq(ZcOrderProcessRecordDO::getStatus, 1));
        }

        LogRecordContext.putVariable("newOrderStatus",
                ZcSalesOrderStatusEnum.valueOf(newOrderStatus).getLabel());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = ZC_SALES_ORDER_CURTAIN_TYPE, subType = ZC_SALES_ORDER_CURTAIN_CANCEL_SHIP_SUB_TYPE,
            bizNo = "{{#id}}", success = ZC_SALES_ORDER_CURTAIN_CANCEL_SHIP_SUCCESS)
    public void cancelShipCurtain(Long id, Long masterId, Long assistantId, String reason) {
        // 1. 校验窗帘行存在，记录操作前状态
        ZcSalesOrderCurtainDO curtain = validateSalesOrderCurtainExists(id);
        Long orderId = curtain.getOrderId();
        String beforeStatus = curtain.getStatus();

        // 发货时间为空说明尚未发货，无需撤销
        if (curtain.getShipTime() == null) {
            throw exception(SALES_ORDER_CURTAIN_NOT_SHIPPED);
        }

        // 2. 回退窗帘行状态：仍保留打包记录则回退为已打包，否则按用料重算配料状态；清空发货时间
        String restoredStatus = curtain.getPackTime() != null
                ? ZcSalesOrderStatusEnum.DABAO.name()
                : orderStatusCalculator.calculateCurtainPeiliaoStatusByCurtainId(id);
        salesOrderCurtainMapper.update(null, Wrappers.<ZcSalesOrderCurtainDO>lambdaUpdate()
                .set(ZcSalesOrderCurtainDO::getStatus, restoredStatus)
                .set(ZcSalesOrderCurtainDO::getShipTime, null)
                .eq(ZcSalesOrderCurtainDO::getId, id));

        // 3. 联动更新订单主表状态
        ZcSalesOrderDO order = salesOrderMapper.selectById(orderId);
        String newOrderStatus = orderStatusCalculator.syncOrderStatus(orderId);

        // 4. 写入取消发货操作记录
        orderOperationLogService.createLog(ZcOrderOperationLogDO.builder()
                .orderId(orderId)
                .orderNo(order != null ? order.getOrderNo() : "")
                .operateType(ZcOrderOperateTypeEnum.CANCEL_SHIP.name())
                .targetType(ZcOrderOperateTargetTypeEnum.CURTAIN.name())
                .targetId(id)
                .beforeStatus(beforeStatus)
                .afterStatus(restoredStatus)
                .orderAfterStatus(newOrderStatus)
                .build());

        // 5. 查询操作员姓名，构建撤销备注（格式：取消的操作员是：{姓名}，原因：{reason}）
        ZcWorkshopUserDO masterUser = workshopUserService.getWorkshopUser(masterId);
        String cancelNote = "取消的操作员是：" + (masterUser != null ? masterUser.getName() : masterId)
                + (reason != null && !reason.isEmpty() ? "，原因：" + reason : "");

        // 6. 查询系统配置的发货工序节点，将该窗帘行已完成的发货工序记录撤销（status 改为 2），并写入备注
        ZcProcessNodeDO shipNode = systemProcessNodeHelper.getSystemNode(ZcSystemProcessNodeEnum.SHIP);
        if (shipNode != null) {
            processRecordMapper.update(null, Wrappers.<ZcOrderProcessRecordDO>lambdaUpdate()
                    .set(ZcOrderProcessRecordDO::getStatus, 2)
                    .set(ZcOrderProcessRecordDO::getNote, cancelNote)
                    .eq(ZcOrderProcessRecordDO::getOrderId, orderId)
                    .eq(ZcOrderProcessRecordDO::getCurtainId, id)
                    .eq(ZcOrderProcessRecordDO::getNodeId, shipNode.getId())
                    .eq(ZcOrderProcessRecordDO::getStatus, 1));
        }

        LogRecordContext.putVariable("newOrderStatus",
                ZcSalesOrderStatusEnum.valueOf(newOrderStatus).getLabel());
    }

    /**
     * 写入窗帘级工序完成记录（仅 orderId + curtainId，不含 structureId / materialId）
     */
    private void insertCurtainProcessRecord(Long orderId, Long curtainId, ZcProcessNodeDO node,
                                            ZcSystemProcessNodeEnum nodeEnum, Long masterId, Long assistantId) {
        ZcOrderProcessRecordScopeHelper.Scope scope = processRecordScopeHelper.normalize(
                orderId, curtainId, null, null);
        processRecordMapper.insert(ZcOrderProcessRecordDO.builder()
                .orderId(scope.getOrderId())
                .curtainId(scope.getCurtainId())
                .structureId(scope.getStructureId())
                .materialId(scope.getMaterialId())
                .nodeId(systemProcessNodeHelper.resolveNodeId(node))
                .nodeName(systemProcessNodeHelper.resolveNodeName(node, nodeEnum))
                .status(1)
                .masterId(masterId)
                .assistantId(assistantId)
                .build());
    }

    private ZcSalesOrderCurtainDO validateSalesOrderCurtainExists(Long id) {
        ZcSalesOrderCurtainDO curtain = salesOrderCurtainMapper.selectById(id);
        if (curtain == null) {
            throw exception(SALES_ORDER_CURTAIN_NOT_EXISTS);
        }
        return curtain;
    }

}
