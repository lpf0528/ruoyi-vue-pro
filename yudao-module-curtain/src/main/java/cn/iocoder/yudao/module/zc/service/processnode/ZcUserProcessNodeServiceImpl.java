package cn.iocoder.yudao.module.zc.service.processnode;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.zc.controller.admin.processnode.vo.ZcProcessNodeRespVO;
import cn.iocoder.yudao.module.zc.controller.admin.processnode.vo.ZcUserProcessNodeSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.processnode.ZcProcessNodeDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.processnode.ZcUserProcessNodeDO;
import cn.iocoder.yudao.module.zc.dal.mysql.processnode.ZcProcessNodeMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.processnode.ZcUserProcessNodeMapper;
import com.mzt.logapi.context.LogRecordContext;
import com.mzt.logapi.starter.annotation.LogRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.USER_PROCESS_NODE_NOT_AUTHORIZED;
import static cn.iocoder.yudao.module.zc.enums.LogRecordConstants.*;

/**
 * 员工-工序节点绑定 Service 实现类
 *
 * @author 01Coder
 */
@Service
@Validated
public class ZcUserProcessNodeServiceImpl implements ZcUserProcessNodeService {

    @Resource
    private ZcUserProcessNodeMapper userProcessNodeMapper;
    @Resource
    private ZcProcessNodeMapper processNodeMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = ZC_USER_PROCESS_NODE_TYPE, subType = ZC_USER_PROCESS_NODE_SAVE_SUB_TYPE, bizNo = "{{#reqVO.userId}}",
            success = ZC_USER_PROCESS_NODE_SAVE_SUCCESS)
    public void saveUserProcessNodes(ZcUserProcessNodeSaveReqVO reqVO) {
        // 1. 清除该员工原有全部绑定关系
        userProcessNodeMapper.deleteByUserId(reqVO.getUserId());

        // 记录操作日志上下文
        LogRecordContext.putVariable("userId", reqVO.getUserId());
        LogRecordContext.putVariable("nodeCount", CollUtil.isEmpty(reqVO.getNodeIds()) ? 0 : reqVO.getNodeIds().size());

        // 2. 重新插入新的绑定关系
        if (CollUtil.isEmpty(reqVO.getNodeIds())) {
            return;
        }
        for (Long nodeId : reqVO.getNodeIds()) {
            ZcUserProcessNodeDO bind = ZcUserProcessNodeDO.builder()
                    .userId(reqVO.getUserId())
                    .nodeId(nodeId)
                    .build();
            userProcessNodeMapper.insert(bind);
        }
    }

    @Override
    public List<ZcProcessNodeRespVO> getUserProcessNodeList(Long userId) {
        return getProcessNodesByUserId(userId);
    }

    @Override
    public List<ZcProcessNodeRespVO> getMyProcessNodeList() {
        Long currentUserId = SecurityFrameworkUtils.getLoginUserId();
        return getProcessNodesByUserId(currentUserId);
    }

    @Override
    public void validateCurrentUserCanOperateNode(Long nodeId) {
        Long currentUserId = SecurityFrameworkUtils.getLoginUserId();
        List<ZcUserProcessNodeDO> binds = userProcessNodeMapper.selectListByUserId(currentUserId);
        Set<Long> authorizedNodeIds = binds.stream()
                .map(ZcUserProcessNodeDO::getNodeId)
                .collect(Collectors.toSet());
        if (!authorizedNodeIds.contains(nodeId)) {
            throw exception(USER_PROCESS_NODE_NOT_AUTHORIZED);
        }
    }

    /** 根据 userId 查询其绑定的节点详情列表，按 sort 升序排列 */
    private List<ZcProcessNodeRespVO> getProcessNodesByUserId(Long userId) {
        List<ZcUserProcessNodeDO> binds = userProcessNodeMapper.selectListByUserId(userId);
        if (CollUtil.isEmpty(binds)) {
            return Collections.emptyList();
        }
        Set<Long> nodeIds = binds.stream()
                .map(ZcUserProcessNodeDO::getNodeId)
                .collect(Collectors.toSet());
        List<ZcProcessNodeDO> nodes = processNodeMapper.selectList(ZcProcessNodeDO::getId, nodeIds);
        nodes.sort((a, b) -> {
            int sa = a.getSort() == null ? 0 : a.getSort();
            int sb = b.getSort() == null ? 0 : b.getSort();
            return Integer.compare(sa, sb);
        });
        return BeanUtils.toBean(nodes, ZcProcessNodeRespVO.class);
    }

}
