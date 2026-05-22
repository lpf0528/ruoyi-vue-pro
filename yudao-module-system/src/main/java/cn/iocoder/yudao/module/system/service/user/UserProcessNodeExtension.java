package cn.iocoder.yudao.module.system.service.user;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 用户工序节点扩展点
 *
 * <p>由 curtain 模块实现，system 模块通过此接口获取用户绑定的工序节点名称，
 * 避免 system ↔ curtain 双向编译依赖。{@code UserController} 通过
 * {@code @Autowired(required = false)} 注入，curtain 模块不启动时自动降级。</p>
 */
public interface UserProcessNodeExtension {

    /**
     * 批量获取用户绑定的工序节点名称列表
     *
     * @param userIds 用户 ID 集合
     * @return userId → 工序节点名称列表（按 sort 升序），如 ["备料", "裁剪", "缝制"]
     */
    Map<Long, List<String>> getProcessNodeNamesByUserIds(Collection<Long> userIds);

}
