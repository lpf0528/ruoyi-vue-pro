package cn.iocoder.yudao.module.zc.service.salesorder;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mzt.logapi.context.LogRecordContext;
import com.mzt.logapi.starter.annotation.LogRecord;

import cn.iocoder.yudao.module.zc.dal.dataobject.orderoperationlog.ZcOrderOperationLogDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.processnode.ZcOrderProcessRecordDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.processnode.ZcProcessNodeDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZcSalesOrderCurtainDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZcSalesOrderDO;
import cn.iocoder.yudao.module.zc.dal.mysql.processnode.ZcOrderProcessRecordMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.processnode.ZcProcessNodeMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.salesorder.ZcSalesOrderCurtainMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.salesorder.ZcSalesOrderMapper;
import cn.iocoder.yudao.module.zc.enums.ZcOrderOperateTargetTypeEnum;
import cn.iocoder.yudao.module.zc.enums.ZcOrderOperateTypeEnum;
import cn.iocoder.yudao.module.zc.enums.ZcSalesOrderStatusEnum;
import cn.iocoder.yudao.module.zc.service.orderoperationlog.ZcOrderOperationLogService;

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
    private ZcProcessNodeMapper processNodeMapper;
    @Resource
    private ZcOrderProcessRecordMapper processRecordMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = ZC_SALES_ORDER_CURTAIN_TYPE, subType = ZC_SALES_ORDER_CURTAIN_PACK_SUB_TYPE,
            bizNo = "{{#id}}", success = ZC_SALES_ORDER_CURTAIN_PACK_SUCCESS)
    public void packCurtain(Long id) {
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

        // 3. 读取最新窗帘行列表（DB 更新已生效），按状态优先级联动更新订单主表状态
        ZcSalesOrderDO order = salesOrderMapper.selectById(orderId);
        List<ZcSalesOrderCurtainDO> allCurtains = salesOrderCurtainMapper.selectListByOrderId(orderId);
        String newOrderStatus = calculateOrderStatusByCurtains(allCurtains);
        salesOrderMapper.updateStatusById(orderId, newOrderStatus);

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
        //    节点不存在时 nodeId 置 null，nodeName 兜底为"打包"
        ZcProcessNodeDO packNode = processNodeMapper.selectOne(
                Wrappers.<ZcProcessNodeDO>lambdaQuery()
                        .eq(ZcProcessNodeDO::getName, "打包")
                        .eq(ZcProcessNodeDO::getGroup, 0));
        processRecordMapper.insert(ZcOrderProcessRecordDO.builder()
                .orderId(orderId)
                .curtainId(id)
                .nodeId(packNode != null ? packNode.getId() : null)
                .nodeName(packNode != null ? packNode.getName() : "打包")
                .status(1)  // 1=完成
                .build());

        LogRecordContext.putVariable("newOrderStatus",
                ZcSalesOrderStatusEnum.valueOf(newOrderStatus).getLabel());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = ZC_SALES_ORDER_CURTAIN_TYPE, subType = ZC_SALES_ORDER_CURTAIN_SHIP_SUB_TYPE,
            bizNo = "{{#id}}", success = ZC_SALES_ORDER_CURTAIN_SHIP_SUCCESS)
    public void shipCurtain(Long id) {
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

        // 3. 读取最新窗帘行列表（DB 更新已生效），按状态优先级联动更新订单主表状态
        ZcSalesOrderDO order = salesOrderMapper.selectById(orderId);
        List<ZcSalesOrderCurtainDO> allCurtains = salesOrderCurtainMapper.selectListByOrderId(orderId);
        String newOrderStatus = calculateOrderStatusByCurtains(allCurtains);
        salesOrderMapper.updateStatusById(orderId, newOrderStatus);

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

        LogRecordContext.putVariable("newOrderStatus",
                ZcSalesOrderStatusEnum.valueOf(newOrderStatus).getLabel());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = ZC_SALES_ORDER_CURTAIN_TYPE, subType = ZC_SALES_ORDER_CURTAIN_CANCEL_PACK_SUB_TYPE,
            bizNo = "{{#id}}", success = ZC_SALES_ORDER_CURTAIN_CANCEL_PACK_SUCCESS)
    public void cancelPackCurtain(Long id) {
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

        // 2. 回退窗帘行状态为已确认，清空打包时间（必须用 LambdaUpdateWrapper，updateById 不会写 null 字段）
        salesOrderCurtainMapper.update(null, Wrappers.<ZcSalesOrderCurtainDO>lambdaUpdate()
                .set(ZcSalesOrderCurtainDO::getStatus, ZcSalesOrderStatusEnum.CONFIRMED.name())
                .set(ZcSalesOrderCurtainDO::getPackTime, null)
                .eq(ZcSalesOrderCurtainDO::getId, id));

        // 3. 读取最新窗帘行列表（DB 更新已生效），按状态优先级联动更新订单主表状态
        List<ZcSalesOrderCurtainDO> allCurtains = salesOrderCurtainMapper.selectListByOrderId(orderId);
        String newOrderStatus = calculateOrderStatusByCurtains(allCurtains);
        salesOrderMapper.updateStatusById(orderId, newOrderStatus);

        // 4. 查询系统配置的打包工序节点，将该窗帘行已完成的打包工序记录撤销（status 改为 2）
        ZcProcessNodeDO packNode = processNodeMapper.selectOne(
                Wrappers.<ZcProcessNodeDO>lambdaQuery()
                        .eq(ZcProcessNodeDO::getName, "打包")
                        .eq(ZcProcessNodeDO::getGroup, 0));
        if (packNode != null) {
            processRecordMapper.update(null, Wrappers.<ZcOrderProcessRecordDO>lambdaUpdate()
                    .set(ZcOrderProcessRecordDO::getStatus, 2)
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
    public void cancelShipCurtain(Long id) {
        // 1. 校验窗帘行存在，记录操作前状态
        ZcSalesOrderCurtainDO curtain = validateSalesOrderCurtainExists(id);
        Long orderId = curtain.getOrderId();
        String beforeStatus = curtain.getStatus();

        // 发货时间为空说明尚未发货，无需撤销
        if (curtain.getShipTime() == null) {
            throw exception(SALES_ORDER_CURTAIN_NOT_SHIPPED);
        }

        // 2. 回退窗帘行状态：已打包则回退为已打包，否则回退为已确认；清空发货时间
        String restoredStatus = curtain.getPackTime() != null
                ? ZcSalesOrderStatusEnum.DABAO.name()
                : ZcSalesOrderStatusEnum.CONFIRMED.name();
        salesOrderCurtainMapper.update(null, Wrappers.<ZcSalesOrderCurtainDO>lambdaUpdate()
                .set(ZcSalesOrderCurtainDO::getStatus, restoredStatus)
                .set(ZcSalesOrderCurtainDO::getShipTime, null)
                .eq(ZcSalesOrderCurtainDO::getId, id));

        // 3. 读取最新窗帘行列表（DB 更新已生效），按状态优先级联动更新订单主表状态
        ZcSalesOrderDO order = salesOrderMapper.selectById(orderId);
        List<ZcSalesOrderCurtainDO> allCurtains = salesOrderCurtainMapper.selectListByOrderId(orderId);
        String newOrderStatus = calculateOrderStatusByCurtains(allCurtains);
        salesOrderMapper.updateStatusById(orderId, newOrderStatus);

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

        LogRecordContext.putVariable("newOrderStatus",
                ZcSalesOrderStatusEnum.valueOf(newOrderStatus).getLabel());
    }

    /**
     * 根据成品订单所有窗帘行的最新状态，按优先级计算订单应展示的聚合状态。
     *
     * <p>优先级由高到低：
     * <ol>
     *   <li>全部已发货 → FAHUO</li>
     *   <li>部分已发货 → BUFEN_FAHUO</li>
     *   <li>全部已打包 → DABAO</li>
     *   <li>部分已打包 → BUFEN_DABAO</li>
     *   <li>其余情况（含已确认等）→ CONFIRMED</li>
     * </ol>
     * 此方法须在 DB 更新后调用，传入的列表必须是最新查询结果。
     * </p>
     */
    private String calculateOrderStatusByCurtains(List<ZcSalesOrderCurtainDO> allCurtains) {
        if (allCurtains.isEmpty()) {
            return ZcSalesOrderStatusEnum.CONFIRMED.name();
        }
        long total = allCurtains.size();
        long fahuoCount = allCurtains.stream()
                .filter(c -> ZcSalesOrderStatusEnum.FAHUO.name().equals(c.getStatus()))
                .count();
        if (fahuoCount == total) return ZcSalesOrderStatusEnum.FAHUO.name();
        if (fahuoCount > 0) return ZcSalesOrderStatusEnum.BUFEN_FAHUO.name();

        long dabaoCount = allCurtains.stream()
                .filter(c -> ZcSalesOrderStatusEnum.DABAO.name().equals(c.getStatus()))
                .count();
        if (dabaoCount == total) return ZcSalesOrderStatusEnum.DABAO.name();
        if (dabaoCount > 0) return ZcSalesOrderStatusEnum.BUFEN_DABAO.name();

        return ZcSalesOrderStatusEnum.CONFIRMED.name();
    }

    private ZcSalesOrderCurtainDO validateSalesOrderCurtainExists(Long id) {
        ZcSalesOrderCurtainDO curtain = salesOrderCurtainMapper.selectById(id);
        if (curtain == null) {
            throw exception(SALES_ORDER_CURTAIN_NOT_EXISTS);
        }
        return curtain;
    }

}
