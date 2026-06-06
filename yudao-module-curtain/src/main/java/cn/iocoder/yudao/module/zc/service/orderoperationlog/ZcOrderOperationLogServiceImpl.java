package cn.iocoder.yudao.module.zc.service.orderoperationlog;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.zc.controller.admin.orderoperationlog.vo.ZcOrderOperationLogPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.orderoperationlog.vo.ZcOrderOperationLogRespVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.orderoperationlog.ZcOrderOperationLogDO;
import cn.iocoder.yudao.module.zc.dal.mysql.orderoperationlog.ZcOrderOperationLogMapper;
import cn.iocoder.yudao.module.zc.enums.ZcOrderOperateTargetTypeEnum;
import cn.iocoder.yudao.module.zc.enums.ZcOrderOperateTypeEnum;

/**
 * 销售订单操作记录 Service 实现类
 *
 * @author 01Coder
 */
@Service
@Validated
public class ZcOrderOperationLogServiceImpl implements ZcOrderOperationLogService {

    @Resource
    private ZcOrderOperationLogMapper orderOperationLogMapper;

    @Override
    public void createLog(ZcOrderOperationLogDO log) {
        orderOperationLogMapper.insert(log);
    }

    @Override
    public List<ZcOrderOperationLogDO> getLogListByOrderId(Long orderId) {
        return orderOperationLogMapper.selectListByOrderId(orderId);
    }

    @Override
    public PageResult<ZcOrderOperationLogRespVO> getLogPage(ZcOrderOperationLogPageReqVO pageReqVO) {
        PageResult<ZcOrderOperationLogDO> page = orderOperationLogMapper.selectPage(pageReqVO);
        // 将枚举值翻译为中文 label，其余字段直接映射
        List<ZcOrderOperationLogRespVO> voList = page.getList().stream()
                .map(this::toRespVO)
                .collect(java.util.stream.Collectors.toList());
        return new PageResult<>(voList, page.getTotal());
    }

    /** 将 DO 转换为 RespVO，补充枚举 label */
    private ZcOrderOperationLogRespVO toRespVO(ZcOrderOperationLogDO log) {
        ZcOrderOperationLogRespVO vo = BeanUtils.toBean(log, ZcOrderOperationLogRespVO.class);
        // 操作类型中文名
        try {
            vo.setOperateTypeLabel(ZcOrderOperateTypeEnum.valueOf(log.getOperateType()).getLabel());
        } catch (IllegalArgumentException ignored) {
        }
        // 操作对象类型中文名
        try {
            vo.setTargetTypeLabel(ZcOrderOperateTargetTypeEnum.valueOf(log.getTargetType()).getLabel());
        } catch (IllegalArgumentException ignored) {
        }
        return vo;
    }

}
