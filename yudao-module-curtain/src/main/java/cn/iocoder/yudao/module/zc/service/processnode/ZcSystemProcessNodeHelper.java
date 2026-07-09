package cn.iocoder.yudao.module.zc.service.processnode;

import cn.iocoder.yudao.module.zc.dal.dataobject.processnode.ZcProcessNodeDO;
import cn.iocoder.yudao.module.zc.dal.mysql.processnode.ZcProcessNodeMapper;
import cn.iocoder.yudao.module.zc.enums.ZcSystemProcessNodeEnum;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 系统内置工序节点辅助类
 *
 * <p>统一按字典名称查询 group=0 工序节点；当前租户不存在时自动创建系统节点。</p>
 */
@Component
public class ZcSystemProcessNodeHelper {

    @Resource
    private ZcProcessNodeMapper processNodeMapper;

    /**
     * 获取系统配置工序节点（group=0）；不存在时为当前租户自动创建
     *
     * @param nodeEnum 系统工序节点枚举
     * @return 工序节点（永不为 null）
     */
    public ZcProcessNodeDO getSystemNode(ZcSystemProcessNodeEnum nodeEnum) {
        ZcProcessNodeDO node = findSystemNode(nodeEnum);
        if (node != null) {
            return node;
        }
        return createSystemNodeIfAbsent(nodeEnum);
    }

    /**
     * 解析工序节点 ID
     *
     * @param node 工序节点
     * @return 节点 ID
     */
    public Long resolveNodeId(ZcProcessNodeDO node) {
        return node != null ? node.getId() : null;
    }

    /**
     * 解析工序名称：节点存在时用节点名，否则用字典兜底名称
     *
     * @param node     工序节点，可为 null
     * @param nodeEnum 系统工序节点枚举
     * @return 工序名称
     */
    public String resolveNodeName(ZcProcessNodeDO node, ZcSystemProcessNodeEnum nodeEnum) {
        if (node != null && node.getName() != null) {
            return node.getName();
        }
        return nodeEnum.getDictLabel();
    }

    private ZcProcessNodeDO findSystemNode(ZcSystemProcessNodeEnum nodeEnum) {
        return processNodeMapper.selectOne(Wrappers.<ZcProcessNodeDO>lambdaQuery()
                .eq(ZcProcessNodeDO::getGroup, 0)
                .eq(ZcProcessNodeDO::getName, nodeEnum.getDictLabel()));
    }

    /**
     * 为当前租户创建系统内置工序节点（group=0）；并发场景下二次查询兜底
     */
    private ZcProcessNodeDO createSystemNodeIfAbsent(ZcSystemProcessNodeEnum nodeEnum) {
        // 双重检查，避免并发重复创建
        ZcProcessNodeDO existing = findSystemNode(nodeEnum);
        if (existing != null) {
            return existing;
        }
        ZcProcessNodeDO newNode = ZcProcessNodeDO.builder()
                .name(nodeEnum.getDictLabel())
                .sort(nodeEnum.getSort())
                .group(0)
                .description("系统自动创建")
                .build();
        processNodeMapper.insert(newNode);
        return newNode;
    }

}
