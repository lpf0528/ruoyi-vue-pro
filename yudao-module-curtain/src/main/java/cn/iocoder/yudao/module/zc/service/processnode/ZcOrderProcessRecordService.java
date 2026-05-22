package cn.iocoder.yudao.module.zc.service.processnode;

import cn.iocoder.yudao.module.zc.controller.admin.processnode.vo.ZcOrderProcessRecordCompleteReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.processnode.vo.ZcOrderProcessRecordRespVO;
import cn.iocoder.yudao.module.zc.controller.admin.processnode.vo.ZcOrderProcessRecordSaveReqVO;

import javax.validation.Valid;
import java.util.List;

/**
 * 订单工序记录 Service 接口
 *
 * <p>工厂员工通过此接口推进订单工序进度。
 * 创建工序记录时会校验员工-节点绑定权限，并自动联动更新订单状态：
 * 第一条记录创建时 pending → processing，同时更新订单的 current_node_name。</p>
 *
 * @author 01Coder
 */
public interface ZcOrderProcessRecordService {

    /**
     * 新增工序记录（开始某道工序）
     *
     * <p>前置校验：<br>
     * 1. 订单必须处于 pending 或 processing 状态；<br>
     * 2. 当前登录员工必须已绑定所选工序节点。</p>
     *
     * @param reqVO 创建信息
     * @return 新记录 ID
     */
    Long createProcessRecord(@Valid ZcOrderProcessRecordSaveReqVO reqVO);

    /**
     * 标记工序完成
     *
     * @param reqVO 包含记录 ID 和完成备注
     */
    void completeProcessRecord(@Valid ZcOrderProcessRecordCompleteReqVO reqVO);

    /**
     * 删除工序记录
     *
     * <p>仅允许删除状态为「进行中」的记录，已完成的记录不可删除。</p>
     *
     * @param id 记录 ID
     */
    void deleteProcessRecord(Long id);

    /**
     * 获取订单的工序时间线，按创建时间升序排列（含操作人昵称）
     *
     * @param orderId 订单 ID
     * @return 工序记录列表
     */
    List<ZcOrderProcessRecordRespVO> getProcessRecordList(Long orderId);

}
