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
 * <p>统一按字典名称查询 group=0 工序节点，并在节点不存在时提供字典兜底名称。</p>
 */
@Component
public class ZcSystemProcessNodeHelper {

    @Resource
    private ZcProcessNodeMapper processNodeMapper;

    /**
     * 查询系统配置工序节点（group=0），按字典名称匹配
     *
     * @param nodeEnum 系统工序节点枚举
     * @return 工序节点，不存在时返回 null
     */
    public ZcProcessNodeDO getSystemNode(ZcSystemProcessNodeEnum nodeEnum) {
        return processNodeMapper.selectOne(Wrappers.<ZcProcessNodeDO>lambdaQuery()
                .eq(ZcProcessNodeDO::getGroup, 0)
                .eq(ZcProcessNodeDO::getName, nodeEnum.getDictLabel()));
    }

    /**
     * 解析工序节点 ID：节点存在时返回 ID，否则返回 null
     *
     * @param node 工序节点
     * @return 节点 ID 或 null
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

}
