package cn.iocoder.yudao.module.zc.service.processnode;

import cn.iocoder.yudao.module.zc.controller.admin.processnode.vo.ZcProcessNodeRespVO;
import cn.iocoder.yudao.module.zc.controller.admin.processnode.vo.ZcUserProcessNodeSaveReqVO;

import javax.validation.Valid;
import java.util.List;

/**
 * 员工-工序节点绑定 Service 接口
 *
 * <p>管理员为员工分配可操作的工序节点；员工新增工序记录前，
 * 通过 {@link #getMyProcessNodeList()} 获取自己可操作的节点列表。</p>
 *
 * @author 01Coder
 */
public interface ZcUserProcessNodeService {

    /**
     * 保存员工的工序节点绑定（覆盖式）
     *
     * <p>先清除该员工原有的全部绑定，再插入新的绑定关系。
     * 传入空列表则清除所有绑定。</p>
     *
     * @param reqVO 包含 userId 和 nodeIds
     */
    void saveUserProcessNodes(@Valid ZcUserProcessNodeSaveReqVO reqVO);

    /**
     * 获取某员工已绑定的工序节点列表（管理员查看/编辑用）
     *
     * @param userId 员工用户 ID
     * @return 已绑定的工序节点列表，按 sort 排序
     */
    List<ZcProcessNodeRespVO> getUserProcessNodeList(Long userId);

    /**
     * 获取当前登录员工可操作的工序节点列表
     *
     * @return 当前用户绑定的工序节点列表，按 sort 排序
     */
    List<ZcProcessNodeRespVO> getMyProcessNodeList();

    /**
     * 校验当前登录用户是否有权限操作指定节点
     *
     * @param nodeId 工序节点 ID
     * @throws cn.iocoder.yudao.framework.common.exception.ServiceException
     *         若无权限，抛出 {@link cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants#USER_PROCESS_NODE_NOT_AUTHORIZED}
     */
    void validateCurrentUserCanOperateNode(Long nodeId);

}
