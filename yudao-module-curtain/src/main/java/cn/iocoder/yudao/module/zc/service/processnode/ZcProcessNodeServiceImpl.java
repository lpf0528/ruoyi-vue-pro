package cn.iocoder.yudao.module.zc.service.processnode;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import cn.iocoder.yudao.module.zc.controller.admin.processnode.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.processnode.ZcProcessNodeDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.processnode.ZcProcessNodeElementDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtainstructureelement.ZcCurtainStructureElementDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.common.util.collection.CollectionUtils;

import cn.iocoder.yudao.module.zc.dal.mysql.processnode.ZcProcessNodeMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.processnode.ZcProcessNodeElementMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.curtainstructureelement.ZcCurtainStructureElementMapper;
import com.mzt.logapi.context.LogRecordContext;
import com.mzt.logapi.service.impl.DiffParseFunction;
import com.mzt.logapi.starter.annotation.LogRecord;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.module.zc.enums.LogRecordConstants.*;

/**
 * 工序节点配置 Service 实现类
 *
 * @author 01Coder
 */
@Service
@Validated
public class ZcProcessNodeServiceImpl implements ZcProcessNodeService {

    @Resource
    private ZcProcessNodeMapper processNodeMapper;

    @Resource
    private ZcProcessNodeElementMapper processNodeElementMapper;

    @Resource
    private ZcCurtainStructureElementMapper curtainStructureElementMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = ZC_PROCESS_NODE_TYPE, subType = ZC_PROCESS_NODE_CREATE_SUB_TYPE, bizNo = "{{#processNode.id}}",
            success = ZC_PROCESS_NODE_CREATE_SUCCESS)
    public Long createProcessNode(ZcProcessNodeSaveReqVO createReqVO) {
        // 校验名称唯一性
        validateProcessNodeNameUnique(null, createReqVO.getName());
        // 插入，分组固定为手工配置（1），不依赖前端传值
        ZcProcessNodeDO processNode = BeanUtils.toBean(createReqVO, ZcProcessNodeDO.class);
        processNode.setGroup(1);
        processNodeMapper.insert(processNode);
        // 保存节点关联组件
        saveProcessNodeElements(processNode.getId(), createReqVO.getElementIds());
        // 记录操作日志上下文
        LogRecordContext.putVariable("processNode", processNode);
        return processNode.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = ZC_PROCESS_NODE_TYPE, subType = ZC_PROCESS_NODE_UPDATE_SUB_TYPE, bizNo = "{{#updateReqVO.id}}",
            success = ZC_PROCESS_NODE_UPDATE_SUCCESS)
    public void updateProcessNode(ZcProcessNodeSaveReqVO updateReqVO) {
        // 校验存在
        ZcProcessNodeDO oldProcessNode = validateProcessNodeExists(updateReqVO.getId());
        // 系统内置工序节点（group=0）禁止编辑
        validateProcessNodeNotSystem(oldProcessNode);
        // 校验名称唯一性（排除自身）
        validateProcessNodeNameUnique(updateReqVO.getId(), updateReqVO.getName());
        // 更新，分组固定为手工配置（1），防止被篡改
        ZcProcessNodeDO updateObj = BeanUtils.toBean(updateReqVO, ZcProcessNodeDO.class);
        updateObj.setGroup(1);
        processNodeMapper.updateById(updateObj);
        // 硬删除之前配置的关联组件，重新创建
        processNodeElementMapper.deleteByProcessNodeId(updateReqVO.getId());
        saveProcessNodeElements(updateReqVO.getId(), updateReqVO.getElementIds());
        // 记录操作日志上下文
        LogRecordContext.putVariable(DiffParseFunction.OLD_OBJECT, BeanUtils.toBean(oldProcessNode, ZcProcessNodeSaveReqVO.class));
        LogRecordContext.putVariable("processNodeName", oldProcessNode.getName());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = ZC_PROCESS_NODE_TYPE, subType = ZC_PROCESS_NODE_DELETE_SUB_TYPE, bizNo = "{{#id}}",
            success = ZC_PROCESS_NODE_DELETE_SUCCESS)
    public void deleteProcessNode(Long id) {
        // 校验存在
        ZcProcessNodeDO processNode = validateProcessNodeExists(id);
        // 系统内置工序节点（group=0）禁止删除
        validateProcessNodeNotSystem(processNode);
        // 记录操作日志上下文
        LogRecordContext.putVariable("processNodeName", processNode.getName());
        // 删除
        processNodeMapper.deleteById(id);
        // 级联硬删除节点关联组件
        processNodeElementMapper.deleteByProcessNodeId(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteProcessNodeListByIds(List<Long> ids) {
        // 批量校验：系统内置工序节点（group=0）禁止删除
        List<ZcProcessNodeDO> nodes = processNodeMapper.selectBatchIds(ids);
        nodes.forEach(this::validateProcessNodeNotSystem);
        // 删除
        processNodeMapper.deleteByIds(ids);
        // 级联硬删除节点关联组件
        ids.forEach(processNodeElementMapper::deleteByProcessNodeId);
    }

    /**
     * 保存节点关联组件（全量新增），elementIds 为空时不做任何操作
     *
     * @param processNodeId 节点ID
     * @param elementIds    关联组件编号列表
     */
    private void saveProcessNodeElements(Long processNodeId, List<Long> elementIds) {
        if (CollectionUtils.isAnyEmpty(elementIds)) {
            return;
        }
        List<ZcProcessNodeElementDO> list = elementIds.stream()
                .map(elementId -> ZcProcessNodeElementDO.builder()
                        .processNodeId(processNodeId)
                        .elementId(elementId)
                        .build())
                .collect(Collectors.toList());
        processNodeElementMapper.insertBatch(list);
    }

    /**
     * 校验工序节点名称的唯一性
     *
     * @param id   更新时传入自身 ID（排除自身），新增时传 null
     * @param name 待校验的名称
     */
    private void validateProcessNodeNameUnique(Long id, String name) {
        if (processNodeMapper.existsByName(name, id)) {
            throw exception(PROCESS_NODE_NAME_EXISTS);
        }
    }

    private ZcProcessNodeDO validateProcessNodeExists(Long id) {
        ZcProcessNodeDO processNode = processNodeMapper.selectById(id);
        if (processNode == null) {
            throw exception(PROCESS_NODE_NOT_EXISTS);
        }
        return processNode;
    }

    /**
     * 校验工序节点不是系统内置节点（group=0），否则抛出异常
     *
     * @param processNode 待校验的工序节点
     */
    private void validateProcessNodeNotSystem(ZcProcessNodeDO processNode) {
        if (Integer.valueOf(0).equals(processNode.getGroup())) {
            throw exception(PROCESS_NODE_SYSTEM_CANNOT_MODIFY);
        }
    }

    @Override
    public ZcProcessNodeDO getProcessNode(Long id) {
        ZcProcessNodeDO processNode = processNodeMapper.selectById(id);
        if (processNode != null) {
            fillProcessNodeElements(Collections.singletonList(processNode));
        }
        return processNode;
    }

    @Override
    public PageResult<ZcProcessNodeDO> getProcessNodePage(ZcProcessNodePageReqVO pageReqVO) {
        PageResult<ZcProcessNodeDO> pageResult = processNodeMapper.selectPage(pageReqVO);
        fillProcessNodeElements(pageResult.getList());
        return pageResult;
    }

    @Override
    public List<ZcProcessNodeDO> getProcessNodeList(ZcProcessNodeListReqVO listReqVO) {
        return processNodeMapper.selectList(listReqVO);
    }

    /**
     * 批量填充节点关联的组件编号、名称，避免 N+1 查询
     *
     * @param processNodes 待填充的节点列表
     */
    private void fillProcessNodeElements(List<ZcProcessNodeDO> processNodes) {
        if (CollectionUtils.isAnyEmpty(processNodes)) {
            return;
        }
        List<Long> processNodeIds = processNodes.stream().map(ZcProcessNodeDO::getId).collect(Collectors.toList());
        List<ZcProcessNodeElementDO> elementRelations = processNodeElementMapper.selectListByProcessNodeIds(processNodeIds);
        if (elementRelations.isEmpty()) {
            return;
        }
        // 批量查询组件名称，避免循环查库
        Set<Long> elementIdSet = elementRelations.stream().map(ZcProcessNodeElementDO::getElementId).collect(Collectors.toSet());
        Map<Long, String> elementNameMap = curtainStructureElementMapper.selectBatchIds(elementIdSet).stream()
                .collect(Collectors.toMap(ZcCurtainStructureElementDO::getId, ZcCurtainStructureElementDO::getName));
        Map<Long, List<ZcProcessNodeElementDO>> relationMap = elementRelations.stream()
                .collect(Collectors.groupingBy(ZcProcessNodeElementDO::getProcessNodeId));
        processNodes.forEach(processNode -> {
            List<ZcProcessNodeElementDO> relations = relationMap.get(processNode.getId());
            if (relations == null) {
                return;
            }
            processNode.setElementIds(relations.stream().map(ZcProcessNodeElementDO::getElementId).collect(Collectors.toList()));
            processNode.setElementNames(relations.stream()
                    .map(relation -> elementNameMap.get(relation.getElementId()))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList()));
        });
    }

}
