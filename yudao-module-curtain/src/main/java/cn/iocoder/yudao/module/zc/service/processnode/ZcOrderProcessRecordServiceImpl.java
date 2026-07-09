package cn.iocoder.yudao.module.zc.service.processnode;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.zc.controller.admin.processnode.vo.ZcOrderProcessRecordRespVO;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.ZcSalesOrderDetailRespVO;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.ZcSalesOrderProcessRecordDetailRespVO;
import cn.iocoder.yudao.module.zc.controller.admin.processnode.vo.ZcOrderProcessRecordRevokeReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.processnode.vo.ZcOrderProcessRecordSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.processnode.ZcOrderProcessRecordDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.processnode.ZcProcessNodeDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZcSalesOrderDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.workshopuser.ZcWorkshopUserDO;
import cn.iocoder.yudao.module.zc.service.salesorder.ZcSalesOrderService;
import cn.iocoder.yudao.module.zc.dal.mysql.processnode.ZcOrderProcessRecordMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.processnode.ZcProcessNodeMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.salesorder.ZcSalesOrderMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.workshopuser.ZcWorkshopUserMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mzt.logapi.context.LogRecordContext;
import com.mzt.logapi.starter.annotation.LogRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertMap;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertSet;
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
    @Resource
    private ZcOrderProcessRecordScopeHelper processRecordScopeHelper;
    @Resource
    private ZcSalesOrderService salesOrderService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = ZC_ORDER_PROCESS_RECORD_TYPE, subType = ZC_ORDER_PROCESS_RECORD_CREATE_SUB_TYPE,
            bizNo = "{{#record.id}}", success = ZC_ORDER_PROCESS_RECORD_CREATE_SUCCESS)
    public Long createProcessRecord(ZcOrderProcessRecordSaveReqVO reqVO) {
        // 1. 校验订单存在，且已确认（confirmTime 不为空后方可记录工序）
        ZcSalesOrderDO order = validateSalesOrderExists(reqVO.getOrderId());
        if (order.getConfirmTime() == null) {
            throw exception(SALES_ORDER_STATUS_CANNOT_PROCESS);
        }

        // 2. 校验工序节点存在，并快照节点名称
        ZcProcessNodeDO node = processNodeMapper.selectById(reqVO.getNodeId());
        if (node == null) {
            throw exception(PROCESS_NODE_NOT_EXISTS);
        }

        // 3. 归一化定位 ID，保证层级完整（用料级补齐 curtain/structure，窗帘级清空 structure/material）
        ZcOrderProcessRecordScopeHelper.Scope scope = processRecordScopeHelper.normalize(
                reqVO.getOrderId(), reqVO.getCurtainId(), reqVO.getStructureId(), reqVO.getMaterialId());

        // 4. 校验在相同范围内该节点尚未执行；已撤销（status=2）的记录不计，允许撤销后重新记录
        if (processRecordMapper.selectCompletedRecord(
                scope.getOrderId(), scope.getCurtainId(),
                scope.getStructureId(), scope.getMaterialId(), reqVO.getNodeId()) != null) {
            throw exception(ORDER_PROCESS_RECORD_NODE_DUPLICATED);
        }

        // 5. 校验主操作人员存在
        validateWorkshopUserExists(reqVO.getMasterId());

        // 6. 若指定了副操作人员，校验其存在
        if (reqVO.getAssistantId() != null) {
            validateWorkshopUserExists(reqVO.getAssistantId());
        }

        // 7. 保存工序记录，status=1（完成），记录即表示工序已执行完毕
        ZcOrderProcessRecordDO record = BeanUtils.toBean(reqVO, ZcOrderProcessRecordDO.class);
        record.setCurtainId(scope.getCurtainId());
        record.setStructureId(scope.getStructureId());
        record.setMaterialId(scope.getMaterialId());
        record.setNodeName(node.getName());
        record.setStatus(1);
        processRecordMapper.insert(record);

        // 8. 同步更新订单当前工序名称快照，方便列表页直接展示进度
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
    public List<ZcOrderProcessRecordRespVO> getProcessRecordList(Long orderId, Long masterId,
                                                                  Long curtainId, Long structureId,
                                                                  Long materialId, Long nodeId,
                                                                  List<Integer> groups) {
        return processRecordMapper.selectListWithVO(orderId, masterId, curtainId, structureId, materialId, nodeId, groups);
    }

    @Override
    public ZcSalesOrderProcessRecordDetailRespVO getSalesOrderProcessRecordDetail(Long orderId) {
        ZcSalesOrderDetailRespVO orderDetail = salesOrderService.getSalesOrderDetail(orderId);
        return ZcSalesOrderProcessRecordBuilder.build(orderDetail,
                listProcessRecords(orderId, null, null, null, null, null, null));
    }

    /**
     * 查询订单工序记录并组装 RespVO（仅查记录表 + 批量补全操作人与节点分组，名称由订单详情骨架提供）
     *
     * @param groups 工序节点分组；为 null 时不过滤，返回全部节点记录
     */
    private List<ZcOrderProcessRecordRespVO> listProcessRecords(Long orderId, Long masterId,
                                                                 Long curtainId, Long structureId,
                                                                 Long materialId, Long nodeId,
                                                                 List<Integer> groups) {
        if (orderId == null) {
            return Collections.emptyList();
        }

        LambdaQueryWrapperX<ZcOrderProcessRecordDO> wrapper = new LambdaQueryWrapperX<ZcOrderProcessRecordDO>()
                .eq(ZcOrderProcessRecordDO::getOrderId, orderId)
                .eqIfPresent(ZcOrderProcessRecordDO::getMasterId, masterId)
                .eqIfPresent(ZcOrderProcessRecordDO::getCurtainId, curtainId)
                .eqIfPresent(ZcOrderProcessRecordDO::getStructureId, structureId)
                .eqIfPresent(ZcOrderProcessRecordDO::getMaterialId, materialId)
                .eqIfPresent(ZcOrderProcessRecordDO::getNodeId, nodeId);

        if (groups != null) {
            if (CollUtil.isEmpty(groups)) {
                return Collections.emptyList();
            }
            List<ZcProcessNodeDO> nodes = processNodeMapper.selectList(
                    new LambdaQueryWrapperX<ZcProcessNodeDO>().in(ZcProcessNodeDO::getGroup, groups));
            if (CollUtil.isEmpty(nodes)) {
                return Collections.emptyList();
            }
            wrapper.in(ZcOrderProcessRecordDO::getNodeId, convertSet(nodes, ZcProcessNodeDO::getId));
        }
        // orderByAsc 未在 LambdaQueryWrapperX 中重写，须单独调用，避免链式返回父类类型
        wrapper.orderByAsc(ZcOrderProcessRecordDO::getCreateTime);

        List<ZcOrderProcessRecordDO> recordList = processRecordMapper.selectList(wrapper);
        if (CollUtil.isEmpty(recordList)) {
            return Collections.emptyList();
        }

        final Map<Long, Integer> nodeGroupMap;
        if (groups == null) {
            Set<Long> nodeIds = convertSet(recordList, ZcOrderProcessRecordDO::getNodeId);
            nodeGroupMap = CollUtil.isEmpty(nodeIds) ? Collections.emptyMap()
                    : convertMap(processNodeMapper.selectList(ZcProcessNodeDO::getId, nodeIds),
                    ZcProcessNodeDO::getId, ZcProcessNodeDO::getGroup);
        } else {
            List<ZcProcessNodeDO> nodes = processNodeMapper.selectList(
                    new LambdaQueryWrapperX<ZcProcessNodeDO>().in(ZcProcessNodeDO::getGroup, groups));
            nodeGroupMap = convertMap(nodes, ZcProcessNodeDO::getId, ZcProcessNodeDO::getGroup);
        }

        Set<Long> userIds = new HashSet<>();
        for (ZcOrderProcessRecordDO record : recordList) {
            if (record.getMasterId() != null) {
                userIds.add(record.getMasterId());
            }
            if (record.getAssistantId() != null) {
                userIds.add(record.getAssistantId());
            }
        }
        Map<Long, String> userNameMap = CollUtil.isEmpty(userIds) ? Collections.emptyMap()
                : convertMap(workshopUserMapper.selectList(ZcWorkshopUserDO::getId, userIds),
                ZcWorkshopUserDO::getId, ZcWorkshopUserDO::getName);

        return recordList.stream().map(record -> {
            ZcOrderProcessRecordRespVO vo = BeanUtils.toBean(record, ZcOrderProcessRecordRespVO.class);
            vo.setNodeGroup(nodeGroupMap.get(record.getNodeId()));
            vo.setMasterName(userNameMap.get(record.getMasterId()));
            vo.setAssistantName(userNameMap.get(record.getAssistantId()));
            return vo;
        }).collect(Collectors.toList());
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
