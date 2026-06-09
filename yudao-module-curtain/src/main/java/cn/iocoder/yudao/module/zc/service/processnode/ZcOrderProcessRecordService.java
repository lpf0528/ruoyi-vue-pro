package cn.iocoder.yudao.module.zc.service.processnode;

import cn.iocoder.yudao.module.zc.controller.admin.processnode.vo.ZcOrderProcessRecordRespVO;
import cn.iocoder.yudao.module.zc.controller.admin.processnode.vo.ZcOrderProcessRecordRevokeReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.processnode.vo.ZcOrderProcessRecordSaveReqVO;

import javax.validation.Valid;
import java.util.List;

/**
 * 订单工序记录 Service 接口
 *
 * <p>工序记录采用完成即记录的模式：
 * 创建记录时 status 默认为 1（完成），可通过撤销接口将 status 改为 2（撤销）。
 * 撤销后的记录方可删除，以保留完整审计轨迹。</p>
 *
 * @author 01Coder
 */
public interface ZcOrderProcessRecordService {

    /**
     * 新增工序记录（记录某道工序已完成）
     *
     * <p>前置校验：<br>
     * 1. 订单必须处于已确认状态；<br>
     * 2. 若指定了车间员工，该员工必须已绑定所选工序节点。</p>
     *
     * @param reqVO 创建信息，含订单、窗帘行、结构行、用料明细及工序节点
     * @return 新记录 ID
     */
    Long createProcessRecord(@Valid ZcOrderProcessRecordSaveReqVO reqVO);

    /**
     * 撤销工序记录
     *
     * <p>仅允许撤销状态为「完成」（status=1）的记录，将 status 更新为 2（撤销）。</p>
     *
     * @param reqVO 包含记录 ID 和撤销原因备注
     */
    void revokeProcessRecord(@Valid ZcOrderProcessRecordRevokeReqVO reqVO);

    /**
     * 删除工序记录
     *
     * <p>仅允许删除状态为「撤销」（status=2）的记录，
     * 已完成的记录必须先撤销方可删除，防止篡改有效生产数据。</p>
     *
     * @param id 记录 ID
     */
    void deleteProcessRecord(Long id);

    /**
     * 获取订单的工序时间线，按创建时间降序排列（含车间员工名称）
     *
     * @param orderId  订单 ID，为 null 时不过滤
     * @param masterId 主操作人员 ID，为 null 时不过滤
     * @return 工序记录列表
     */
    List<ZcOrderProcessRecordRespVO> getProcessRecordList(Long orderId, Long masterId);

}
