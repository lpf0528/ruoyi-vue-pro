package cn.iocoder.yudao.module.zc.service.orderoperationlog;

import java.util.List;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.zc.controller.admin.orderoperationlog.vo.ZcOrderOperationLogPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.orderoperationlog.vo.ZcOrderOperationLogRespVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.orderoperationlog.ZcOrderOperationLogDO;

/**
 * 销售订单操作记录 Service 接口
 *
 * @author 01Coder
 */
public interface ZcOrderOperationLogService {

    /**
     * 写入订单操作记录
     *
     * <p>由各业务 Service 在执行关键操作后调用，记录操作快照。
     * 调用方负责填充 DO 各字段，本方法只负责持久化。</p>
     *
     * @param log 操作记录 DO，字段含义参见 {@link ZcOrderOperationLogDO}
     */
    void createLog(ZcOrderOperationLogDO log);

    /**
     * 查询指定订单的全量操作记录（按时间升序）
     *
     * @param orderId 销售订单 ID
     * @return 操作记录列表
     */
    List<ZcOrderOperationLogDO> getLogListByOrderId(Long orderId);

    /**
     * 分页查询订单操作记录
     *
     * <p>支持按订单 ID、操作对象类型、对象 ID、操作类型、是否撤销等条件过滤，
     * 并自动将枚举值翻译为中文 label 填充到 RespVO。</p>
     *
     * @param pageReqVO 分页查询条件
     * @return 分页结果
     */
    PageResult<ZcOrderOperationLogRespVO> getLogPage(ZcOrderOperationLogPageReqVO pageReqVO);

}
