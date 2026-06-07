package cn.iocoder.yudao.module.zc.service.processnode;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.iocoder.yudao.module.zc.controller.admin.processnode.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.processnode.ZcProcessNodeDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.zc.dal.mysql.processnode.ZcProcessNodeMapper;
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

    @Override
    @LogRecord(type = ZC_PROCESS_NODE_TYPE, subType = ZC_PROCESS_NODE_CREATE_SUB_TYPE, bizNo = "{{#processNode.id}}",
            success = ZC_PROCESS_NODE_CREATE_SUCCESS)
    public Long createProcessNode(ZcProcessNodeSaveReqVO createReqVO) {
        // 校验名称唯一性
        validateProcessNodeNameUnique(null, createReqVO.getName());
        // 插入，分组固定为手工配置（1），不依赖前端传值
        ZcProcessNodeDO processNode = BeanUtils.toBean(createReqVO, ZcProcessNodeDO.class);
        processNode.setGroup(1);
        processNodeMapper.insert(processNode);
        // 记录操作日志上下文
        LogRecordContext.putVariable("processNode", processNode);
        return processNode.getId();
    }

    @Override
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
        // 记录操作日志上下文
        LogRecordContext.putVariable(DiffParseFunction.OLD_OBJECT, BeanUtils.toBean(oldProcessNode, ZcProcessNodeSaveReqVO.class));
        LogRecordContext.putVariable("processNodeName", oldProcessNode.getName());
    }

    @Override
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
    }

    @Override
    public void deleteProcessNodeListByIds(List<Long> ids) {
        // 批量校验：系统内置工序节点（group=0）禁止删除
        List<ZcProcessNodeDO> nodes = processNodeMapper.selectBatchIds(ids);
        nodes.forEach(this::validateProcessNodeNotSystem);
        // 删除
        processNodeMapper.deleteByIds(ids);
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
        return processNodeMapper.selectById(id);
    }

    @Override
    public PageResult<ZcProcessNodeDO> getProcessNodePage(ZcProcessNodePageReqVO pageReqVO) {
        return processNodeMapper.selectPage(pageReqVO);
    }

    @Override
    public List<ZcProcessNodeDO> getProcessNodeList(ZcProcessNodeListReqVO listReqVO) {
        return processNodeMapper.selectList(listReqVO);
    }

}
