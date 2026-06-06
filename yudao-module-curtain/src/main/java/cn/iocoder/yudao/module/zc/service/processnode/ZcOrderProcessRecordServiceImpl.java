package cn.iocoder.yudao.module.zc.service.processnode;

import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.zc.controller.admin.processnode.vo.ZcOrderProcessRecordCompleteReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.processnode.vo.ZcOrderProcessRecordRespVO;
import cn.iocoder.yudao.module.zc.controller.admin.processnode.vo.ZcOrderProcessRecordSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.processnode.ZcOrderProcessRecordDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.processnode.ZcProcessNodeDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZcSalesOrderDO;
import cn.iocoder.yudao.module.zc.dal.mysql.processnode.ZcOrderProcessRecordMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.processnode.ZcProcessNodeMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.salesorder.ZcSalesOrderMapper;
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
import cn.iocoder.yudao.module.zc.enums.ZcSalesOrderStatusEnum;
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
    private ZcUserProcessNodeService userProcessNodeService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = ZC_ORDER_PROCESS_RECORD_TYPE, subType = ZC_ORDER_PROCESS_RECORD_CREATE_SUB_TYPE, bizNo = "{{#record.id}}",
            success = ZC_ORDER_PROCESS_RECORD_CREATE_SUCCESS)
    public Long createProcessRecord(ZcOrderProcessRecordSaveReqVO reqVO) {
        // 1. 校验订单存在，且处于已确认状态（确认后方可记录工序）
        ZcSalesOrderDO order = validateSalesOrderExists(reqVO.getOrderId());
        if (!ZcSalesOrderStatusEnum.CONFIRMED.name().equals(order.getStatus())) {
            throw exception(SALES_ORDER_STATUS_CANNOT_PROCESS);
        }

        // 2. 校验当前登录员工是否有权限操作该节点（必须在其绑定列表内）
        userProcessNodeService.validateCurrentUserCanOperateNode(reqVO.getNodeId());

        // 3. 读取节点名称，快照存入记录，防止节点名称后续修改导致历史记录失真
        ZcProcessNodeDO node = processNodeMapper.selectById(reqVO.getNodeId());
        if (node == null) {
            throw exception(PROCESS_NODE_NOT_EXISTS);
        }

        // 4. 保存工序记录，状态默认为进行中
        ZcOrderProcessRecordDO record = BeanUtils.toBean(reqVO, ZcOrderProcessRecordDO.class);
        record.setNodeName(node.getName());
        record.setStatus(1); // 1=进行中
        record.setOperatorUserId(SecurityFrameworkUtils.getLoginUserId());
        processRecordMapper.insert(record);

        // 5. 同步更新订单当前工序名称（状态保持 CONFIRMED，由打包/发货操作推进后续状态）
        salesOrderMapper.update(null, Wrappers.<ZcSalesOrderDO>lambdaUpdate()
                .set(ZcSalesOrderDO::getCurrentNodeName, node.getName())
                .eq(ZcSalesOrderDO::getId, reqVO.getOrderId()));

        // 记录操作日志上下文
        LogRecordContext.putVariable("orderNo", order.getOrderNo());
        LogRecordContext.putVariable("nodeName", node.getName());
        return record.getId();
    }

    @Override
    @LogRecord(type = ZC_ORDER_PROCESS_RECORD_TYPE, subType = ZC_ORDER_PROCESS_RECORD_COMPLETE_SUB_TYPE, bizNo = "{{#reqVO.id}}",
            success = ZC_ORDER_PROCESS_RECORD_COMPLETE_SUCCESS)
    public void completeProcessRecord(ZcOrderProcessRecordCompleteReqVO reqVO) {
        ZcOrderProcessRecordDO record = validateProcessRecordExists(reqVO.getId());
        // 更新状态为已完成，写入完成备注
        processRecordMapper.update(null, Wrappers.<ZcOrderProcessRecordDO>lambdaUpdate()
                .set(ZcOrderProcessRecordDO::getStatus, 2) // 2=已完成
                .set(ZcOrderProcessRecordDO::getNote, reqVO.getNote())
                .eq(ZcOrderProcessRecordDO::getId, reqVO.getId()));
        // 记录操作日志上下文
        ZcSalesOrderDO order = validateSalesOrderExists(record.getOrderId());
        LogRecordContext.putVariable("orderNo", order.getOrderNo());
        LogRecordContext.putVariable("nodeName", record.getNodeName());
    }

    @Override
    @LogRecord(type = ZC_ORDER_PROCESS_RECORD_TYPE, subType = ZC_ORDER_PROCESS_RECORD_DELETE_SUB_TYPE, bizNo = "{{#id}}",
            success = ZC_ORDER_PROCESS_RECORD_DELETE_SUCCESS)
    public void deleteProcessRecord(Long id) {
        ZcOrderProcessRecordDO record = validateProcessRecordExists(id);
        // 已完成的记录不允许删除，防止历史数据被篡改
        if (Integer.valueOf(2).equals(record.getStatus())) {
            throw exception(ORDER_PROCESS_RECORD_ALREADY_COMPLETED);
        }
        // 记录操作日志上下文
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

}
