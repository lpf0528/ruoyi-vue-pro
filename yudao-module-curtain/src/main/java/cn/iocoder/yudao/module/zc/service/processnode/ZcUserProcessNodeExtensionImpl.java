package cn.iocoder.yudao.module.zc.service.processnode;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.module.system.service.user.UserProcessNodeExtension;
import cn.iocoder.yudao.module.zc.dal.dataobject.processnode.ZcProcessNodeDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.processnode.ZcUserProcessNodeDO;
import cn.iocoder.yudao.module.zc.dal.mysql.processnode.ZcProcessNodeMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.processnode.ZcUserProcessNodeMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * {@link UserProcessNodeExtension} 的 curtain 模块实现
 *
 * <p>供 system 模块的用户分页列表接口使用，批量查询每个用户绑定的工序节点名称并拼接成字符串，
 * 通过扩展接口避免 system ↔ curtain 循环依赖。</p>
 */
@Component
public class ZcUserProcessNodeExtensionImpl implements UserProcessNodeExtension {

    @Resource
    private ZcUserProcessNodeMapper userProcessNodeMapper;
    @Resource
    private ZcProcessNodeMapper processNodeMapper;

    @Override
    public Map<Long, List<String>> getProcessNodeNamesByUserIds(Collection<Long> userIds) {
        if (CollUtil.isEmpty(userIds)) {
            return Collections.emptyMap();
        }
        // 1. 批量查询所有绑定关系，一次 SQL 避免 N+1
        List<ZcUserProcessNodeDO> binds = userProcessNodeMapper.selectListByUserIds(userIds);
        if (CollUtil.isEmpty(binds)) {
            return Collections.emptyMap();
        }
        // 2. 批量查询节点详情（含 sort 字段用于排序）
        Set<Long> nodeIds = binds.stream()
                .map(ZcUserProcessNodeDO::getNodeId)
                .collect(Collectors.toSet());
        List<ZcProcessNodeDO> nodes = processNodeMapper.selectBatchIds(nodeIds);
        Map<Long, ZcProcessNodeDO> nodeMap = nodes.stream()
                .collect(Collectors.toMap(ZcProcessNodeDO::getId, n -> n));

        // 3. 按 userId 分组，将节点按 sort 升序排列后收集名称列表
        Map<Long, List<Long>> userNodeIdsMap = binds.stream()
                .collect(Collectors.groupingBy(
                        ZcUserProcessNodeDO::getUserId,
                        Collectors.mapping(ZcUserProcessNodeDO::getNodeId, Collectors.toList())));

        Map<Long, List<String>> result = new HashMap<>();
        userNodeIdsMap.forEach((userId, bindNodeIds) -> {
            List<String> names = bindNodeIds.stream()
                    .map(nodeMap::get)
                    .filter(Objects::nonNull)
                    .sorted(Comparator.comparingInt(n -> n.getSort() == null ? 0 : n.getSort()))
                    .map(ZcProcessNodeDO::getName)
                    .collect(Collectors.toList());
            result.put(userId, names);
        });
        return result;
    }

}
