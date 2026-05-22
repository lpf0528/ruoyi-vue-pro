package cn.iocoder.yudao.module.zc.service.processnode;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.zc.controller.admin.processnode.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.processnode.ZcProcessNodeDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 工序节点配置 Service 接口
 *
 * @author 01Coder
 */
public interface ZcProcessNodeService {

    /**
     * 创建工序节点配置
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProcessNode(@Valid ZcProcessNodeSaveReqVO createReqVO);

    /**
     * 更新工序节点配置
     *
     * @param updateReqVO 更新信息
     */
    void updateProcessNode(@Valid ZcProcessNodeSaveReqVO updateReqVO);

    /**
     * 删除工序节点配置
     *
     * @param id 编号
     */
    void deleteProcessNode(Long id);

    /**
    * 批量删除工序节点配置
    *
    * @param ids 编号
    */
    void deleteProcessNodeListByIds(List<Long> ids);

    /**
     * 获得工序节点配置
     *
     * @param id 编号
     * @return 工序节点配置
     */
    ZcProcessNodeDO getProcessNode(Long id);

    /**
     * 获得工序节点配置分页
     *
     * @param pageReqVO 分页查询
     * @return 工序节点配置分页
     */
    PageResult<ZcProcessNodeDO> getProcessNodePage(ZcProcessNodePageReqVO pageReqVO);

    /**
     * 获得工序节点配置列表，用于前端下拉选项
     *
     * @param listReqVO 列表查询条件
     * @return 工序节点配置列表
     */
    List<ZcProcessNodeDO> getProcessNodeList(ZcProcessNodeListReqVO listReqVO);

}