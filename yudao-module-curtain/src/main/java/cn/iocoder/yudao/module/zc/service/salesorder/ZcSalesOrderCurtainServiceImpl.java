package cn.iocoder.yudao.module.zc.service.salesorder;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;
import com.mzt.logapi.context.LogRecordContext;
import com.mzt.logapi.starter.annotation.LogRecord;

import cn.iocoder.yudao.module.zc.dal.dataobject.orderoperationlog.ZcOrderOperationLogDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZcSalesOrderCurtainDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZcSalesOrderDO;
import cn.iocoder.yudao.module.zc.dal.mysql.orderoperationlog.ZcOrderOperationLogMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.salesorder.ZcSalesOrderCurtainMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.salesorder.ZcSalesOrderMapper;
import cn.iocoder.yudao.module.zc.enums.ZcOrderOperateTargetTypeEnum;
import cn.iocoder.yudao.module.zc.enums.ZcOrderOperateTypeEnum;
import cn.iocoder.yudao.module.zc.enums.ZcSalesOrderStatusEnum;
import cn.iocoder.yudao.module.zc.service.orderoperationlog.ZcOrderOperationLogService;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.SALES_ORDER_CURTAIN_ALREADY_PACKED;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.SALES_ORDER_CURTAIN_ALREADY_SHIPPED;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.SALES_ORDER_CURTAIN_NOT_EXISTS;
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
    private ZcOrderOperationLogMapper orderOperationLogMapper;
    @Resource
    private ZcOrderOperationLogService orderOperationLogService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = ZC_SALES_ORDER_CURTAIN_TYPE, subType = ZC_SALES_ORDER_CURTAIN_PACK_SUB_TYPE,
            bizNo = "{{#id}}", success = ZC_SALES_ORDER_CURTAIN_PACK_SUCCESS)
    public void packCurtain(Long id) {
        // 1. 校验窗帘行存在，记录操作前状态
        ZcSalesOrderCurtainDO curtain = validateSalesOrderCurtainExists(id);
        Long orderId = curtain.getOrderId();
        String beforeStatus = curtain.getStatus();

        // 校验该窗帘行是否已存在未撤销的打包记录，防止重复打包
        if (orderOperationLogMapper.existsActiveLog(
                ZcOrderOperateTargetTypeEnum.CURTAIN.name(), id, ZcOrderOperateTypeEnum.PACK.name())) {
            throw exception(SALES_ORDER_CURTAIN_ALREADY_PACKED);
        }

        // 2. 更新窗帘行状态为已打包，同步记录打包时间
        ZcSalesOrderCurtainDO updateObj = new ZcSalesOrderCurtainDO();
        updateObj.setId(id);
        updateObj.setStatus(ZcSalesOrderStatusEnum.DABAO.name());
        updateObj.setPackTime(LocalDateTime.now());
        salesOrderCurtainMapper.updateById(updateObj);

        // 3. 若订单不处于发货状态，则根据所有窗帘行是否全部已打包来联动更新订单状态
        ZcSalesOrderDO order = salesOrderMapper.selectById(orderId);
        String newOrderStatus;
        if (order != null
                && !ZcSalesOrderStatusEnum.BUFEN_FAHUO.name().equals(order.getStatus())
                && !ZcSalesOrderStatusEnum.FAHUO.name().equals(order.getStatus())) {
            List<ZcSalesOrderCurtainDO> allCurtains = salesOrderCurtainMapper.selectListByOrderId(orderId);
            boolean allPacked = allCurtains.stream()
                    .allMatch(c -> ZcSalesOrderStatusEnum.DABAO.name().equals(c.getStatus()));
            newOrderStatus = allPacked
                    ? ZcSalesOrderStatusEnum.DABAO.name()
                    : ZcSalesOrderStatusEnum.BUFEN_DABAO.name();
            salesOrderMapper.updateStatusById(orderId, newOrderStatus);
        } else {
            // 订单已在发货状态，不回退订单状态，以发货状态为准
            newOrderStatus = order != null ? order.getStatus() : "";
        }

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
                .revoked(false)
                .build());

        // 记录操作日志上下文
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

        // 校验该窗帘行是否已存在未撤销的发货记录，防止重复发货
        if (orderOperationLogMapper.existsActiveLog(
                ZcOrderOperateTargetTypeEnum.CURTAIN.name(), id, ZcOrderOperateTypeEnum.SHIP.name())) {
            throw exception(SALES_ORDER_CURTAIN_ALREADY_SHIPPED);
        }

        // 2. 更新窗帘行状态为已发货，同步记录发货时间
        ZcSalesOrderCurtainDO updateObj = new ZcSalesOrderCurtainDO();
        updateObj.setId(id);
        updateObj.setStatus(ZcSalesOrderStatusEnum.FAHUO.name());
        updateObj.setShipTime(LocalDateTime.now());
        salesOrderCurtainMapper.updateById(updateObj);

        // 3. 检查该订单下所有窗帘行是否全部已发货，联动更新订单状态
        ZcSalesOrderDO order = salesOrderMapper.selectById(orderId);
        List<ZcSalesOrderCurtainDO> allCurtains = salesOrderCurtainMapper.selectListByOrderId(orderId);
        boolean allShipped = allCurtains.stream()
                .allMatch(c -> ZcSalesOrderStatusEnum.FAHUO.name().equals(c.getStatus()));
        String newOrderStatus = allShipped
                ? ZcSalesOrderStatusEnum.FAHUO.name()
                : ZcSalesOrderStatusEnum.BUFEN_FAHUO.name();
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
                .revoked(false)
                .build());

        // 记录操作日志上下文
        LogRecordContext.putVariable("newOrderStatus",
                ZcSalesOrderStatusEnum.valueOf(newOrderStatus).getLabel());
    }

    private ZcSalesOrderCurtainDO validateSalesOrderCurtainExists(Long id) {
        ZcSalesOrderCurtainDO curtain = salesOrderCurtainMapper.selectById(id);
        if (curtain == null) {
            throw exception(SALES_ORDER_CURTAIN_NOT_EXISTS);
        }
        return curtain;
    }

}
