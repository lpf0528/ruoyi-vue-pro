package cn.iocoder.yudao.module.zc.service.progress;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.zc.controller.admin.vo.progress.ZcOrderProgressAppendReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.progress.ZcOrderProgressLogPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.progress.ZcProductionQueueUpdateReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.progress.ZcProgressDefinitionDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.progress.ZcSalesOrderProductionQueueDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.progress.ZcSalesOrderProgressLogDO;
import cn.iocoder.yudao.module.zc.dal.mysql.progress.ZcProgressDefinitionMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.progress.ZcSalesOrderProductionQueueMapper;
import cn.iocoder.yudao.module.zc.dal.mysql.progress.ZcSalesOrderProgressLogMapper;
import cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants;
import cn.iocoder.yudao.module.zc.enums.ZcBizConstants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service
@Validated
public class ZcOrderProgressServiceImpl implements ZcOrderProgressService {

    /** 生产工序 */
    private static final int KIND_PRODUCTION = 2;

    @Resource
    private ZcSalesOrderProgressLogMapper progressLogMapper;
    @Resource
    private ZcProgressDefinitionMapper progressDefinitionMapper;
    @Resource
    private ZcSalesOrderProductionQueueMapper productionQueueMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void appendLog(Long orderId, Long definitionId, String progressCode, String progressName,
                          String actionType, LocalDateTime bizTime, String remark) {
        Long userId = SecurityFrameworkUtils.getLoginUserId();
        ZcSalesOrderProgressLogDO log = ZcSalesOrderProgressLogDO.builder()
                .orderId(orderId)
                .definitionId(definitionId)
                .progressCode(progressCode)
                .progressName(progressName)
                .actionType(actionType)
                .operatorId(userId)
                .bizTime(bizTime != null ? bizTime : LocalDateTime.now())
                .remark(remark)
                .build();
        progressLogMapper.insert(log);
    }

    @Override
    public void appendLog(ZcOrderProgressAppendReqVO reqVO) {
        appendLog(reqVO.getOrderId(), reqVO.getDefinitionId(), reqVO.getProgressCode(), reqVO.getProgressName(),
                reqVO.getActionType(), reqVO.getBizTime(), reqVO.getRemark());
    }

    @Override
    public PageResult<ZcSalesOrderProgressLogDO> getProgressLogPage(ZcOrderProgressLogPageReqVO pageReqVO) {
        return progressLogMapper.selectPage(pageReqVO, new LambdaQueryWrapperX<ZcSalesOrderProgressLogDO>()
                .eq(ZcSalesOrderProgressLogDO::getOrderId, pageReqVO.getOrderId())
                .orderByDesc(ZcSalesOrderProgressLogDO::getBizTime)
                .orderByDesc(ZcSalesOrderProgressLogDO::getId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void initProductionQueue(Long orderId) {
        productionQueueMapper.delete(new LambdaQueryWrapperX<ZcSalesOrderProductionQueueDO>()
                .eq(ZcSalesOrderProductionQueueDO::getOrderId, orderId));
        List<ZcProgressDefinitionDO> defs = progressDefinitionMapper.selectList(
                new LambdaQueryWrapperX<ZcProgressDefinitionDO>()
                        .eq(ZcProgressDefinitionDO::getProgressKind, KIND_PRODUCTION)
                        .eq(ZcProgressDefinitionDO::getStatus, 0)
                        .orderByAsc(ZcProgressDefinitionDO::getSort));
        int seq = 0;
        for (ZcProgressDefinitionDO d : defs) {
            ZcSalesOrderProductionQueueDO q = ZcSalesOrderProductionQueueDO.builder()
                    .orderId(orderId)
                    .definitionId(d.getId())
                    .queueStatus(0)
                    .sequenceNo(seq++)
                    .build();
            productionQueueMapper.insert(q);
        }
    }

    @Override
    public List<ZcSalesOrderProductionQueueDO> listProductionQueue(Long orderId) {
        return productionQueueMapper.selectList(new LambdaQueryWrapperX<ZcSalesOrderProductionQueueDO>()
                .eq(ZcSalesOrderProductionQueueDO::getOrderId, orderId)
                .orderByAsc(ZcSalesOrderProductionQueueDO::getSequenceNo));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateProductionQueue(ZcProductionQueueUpdateReqVO reqVO) {
        ZcSalesOrderProductionQueueDO row = productionQueueMapper.selectById(reqVO.getId());
        if (row == null) {
            throw exception(ErrorCodeConstants.PRODUCTION_QUEUE_NOT_EXISTS);
        }
        ZcSalesOrderProductionQueueDO u = new ZcSalesOrderProductionQueueDO();
        u.setId(reqVO.getId());
        u.setQueueStatus(reqVO.getQueueStatus());
        u.setRemark(reqVO.getRemark());
        Long userId = SecurityFrameworkUtils.getLoginUserId();
        u.setOperatorId(userId);
        int status = reqVO.getQueueStatus();
        if (status == ZcBizConstants.QUEUE_DOING && row.getStartedTime() == null) {
            u.setStartedTime(LocalDateTime.now());
        }
        if (status == ZcBizConstants.QUEUE_DONE) {
            u.setCompletedTime(LocalDateTime.now());
        }
        productionQueueMapper.updateById(u);
    }

}
