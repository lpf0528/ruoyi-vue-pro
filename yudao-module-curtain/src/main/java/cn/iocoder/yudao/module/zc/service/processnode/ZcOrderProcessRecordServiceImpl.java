package cn.iocoder.yudao.module.zc.service.processnode;

import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.zc.controller.admin.processnode.vo.ZcOrderProcessRecordRespVO;
import cn.iocoder.yudao.module.zc.controller.admin.processnode.vo.ZcOrderProcessRecordRevokeReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.processnode.vo.ZcOrderProcessRecordSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.processnode.ZcOrderProcessRecordDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.processnode.ZcProcessNodeDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZcSalesOrderDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.workshopuser.ZcWorkshopUserDO;
import cn.iocoder.yudao.module.zc.dal.mysql.processnode.ZcOrderProcessRecordMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.processnode.ZcProcessNodeMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.salesorder.ZcSalesOrderMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.workshopuser.ZcWorkshopUserMapper;
import cn.iocoder.yudao.module.zc.enums.ZcSalesOrderStatusEnum;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mzt.logapi.context.LogRecordContext;
import com.mzt.logapi.starter.annotation.LogRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.module.zc.enums.LogRecordConstants.*;

/**
 * 订单工序记录 Service 实现类
 *
 * @author 01Coder
 */
@Service
@Validated
public class ZcOrderProcessRecordServiceImpl implements ZcOrderProcessRecordService {

    @Resource
    private ZcOrderProcessRecordMapper processRecordMapper;
    @Resource
    private ZcProcessNodeMapper processNodeMapper;
    @Resource
    private ZcSalesOrderMapper salesOrderMapper;
    @Resource
    private ZcWorkshopUserMapper workshopUserMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = ZC_ORDER_PROCESS_RECORD_TYPE, subType = ZC_ORDER_PROCESS_RECORD_CREATE_SUB_TYPE,
            bizNo = "{{#record.id}}", success = ZC_ORDER_PROCESS_RECORD_CREATE_SUCCESS)
    public Long createProcessRecord(ZcOrderProcessRecordSaveReqVO reqVO) {
        // 1. 校验订单存在，且处于已确认状态（确认后方可记录工序）
        ZcSalesOrderDO order = validateSalesOrderExists(reqVO.getOrderId());
        if (!ZcSalesOrderStatusEnum.CONFIRMED.name().equals(order.getStatus())) {
            throw exception(SALES_ORDER_STATUS_CANNOT_PROCESS);
        }

        // 2. 校验工序节点存在，并快照节点名称
        ZcProcessNodeDO node = processNodeMapper.selectById(reqVO.getNodeId());
        if (node == null) {
            throw exception(PROCESS_NODE_NOT_EXISTS);
        }

        // 3. 校验主操作人员存在
        validateWorkshopUserExists(reqVO.getMasterId());

        // 4. 若指定了副操作人员，校验其存在
        if (reqVO.getAssistantId() != null) {
            validateWorkshopUserExists(reqVO.getAssistantId());
        }

        // 5. 保存工序记录，status=1（完成），记录即表示工序已执行完毕
        ZcOrderProcessRecordDO record = BeanUtils.toBean(reqVO, ZcOrderProcessRecordDO.class);
        record.setNodeName(node.getName());
        record.setStatus(1);
        processRecordMapper.insert(record);

        // 6. 同步更新订单当前工序名称快照，方便列表页直接展示进度
        salesOrderMapper.update(null, Wrappers.<ZcSalesOrderDO>lambdaUpdate()
                .set(ZcSalesOrderDO::getCurrentNodeName, node.getName())
                .eq(ZcSalesOrderDO::getId, reqVO.getOrderId()));

        LogRecordContext.putVariable("orderNo", order.getOrderNo());
        LogRecordContext.putVariable("nodeName", node.getName());
        return record.getId();
    }

    @Override
    @LogRecord(type = ZC_ORDER_PROCESS_RECORD_TYPE, subType = ZC_ORDER_PROCESS_RECORD_REVOKE_SUB_TYPE,
            bizNo = "{{#reqVO.id}}", success = ZC_ORDER_PROCESS_RECORD_REVOKE_SUCCESS)
    public void revokeProcessRecord(ZcOrderProcessRecordRevokeReqVO reqVO) {
        ZcOrderProcessRecordDO record = validateProcessRecordExists(reqVO.getId());
        // 只有完成状态（status=1）的记录才允许撤销
        if (Integer.valueOf(2).equals(record.getStatus())) {
            throw exception(ORDER_PROCESS_RECORD_ALREADY_REVOKED);
        }
        processRecordMapper.update(null, Wrappers.<ZcOrderProcessRecordDO>lambdaUpdate()
                .set(ZcOrderProcessRecordDO::getStatus, 2)
                .set(ZcOrderProcessRecordDO::getNote, reqVO.getNote())
                .eq(ZcOrderProcessRecordDO::getId, reqVO.getId()));

        ZcSalesOrderDO order = validateSalesOrderExists(record.getOrderId());
        LogRecordContext.putVariable("orderNo", order.getOrderNo());
        LogRecordContext.putVariable("nodeName", record.getNodeName());
    }

    @Override
    @LogRecord(type = ZC_ORDER_PROCESS_RECORD_TYPE, subType = ZC_ORDER_PROCESS_RECORD_DELETE_SUB_TYPE,
            bizNo = "{{#id}}", success = ZC_ORDER_PROCESS_RECORD_DELETE_SUCCESS)
    public void deleteProcessRecord(Long id) {
        ZcOrderProcessRecordDO record = validateProcessRecordExists(id);
        // 已完成（status=1）的记录不允许直接删除，必须先撤销
        if (Integer.valueOf(1).equals(record.getStatus())) {
            throw exception(ORDER_PROCESS_RECORD_ALREADY_COMPLETED);
        }
        LogRecordContext.putVariable("recordId", id);
        processRecordMapper.deleteById(id);
    }

    @Override
    public List<ZcOrderProcessRecordRespVO> getProcessRecordList(Long orderId) {
        return processRecordMapper.selectListWithUserByOrderId(orderId);
    }

    private ZcSalesOrderDO validateSalesOrderExists(Long orderId) {
        ZcSalesOrderDO order = salesOrderMapper.selectById(orderId);
        if (order == null) {
            throw exception(SALES_ORDER_NOT_EXISTS);
        }
        return order;
    }

    private ZcOrderProcessRecordDO validateProcessRecordExists(Long id) {
        ZcOrderProcessRecordDO record = processRecordMapper.selectById(id);
        if (record == null) {
            throw exception(ORDER_PROCESS_RECORD_NOT_EXISTS);
        }
        return record;
    }

    /**
     * 校验车间员工存在
     *
     * @param workshopUserId 车间员工 ID
     */
    private void validateWorkshopUserExists(Long workshopUserId) {
        ZcWorkshopUserDO user = workshopUserMapper.selectById(workshopUserId);
        if (user == null) {
            throw exception(WORKSHOP_USER_NOT_EXISTS);
        }
    }

}
