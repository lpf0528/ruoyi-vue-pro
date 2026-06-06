package cn.iocoder.yudao.module.zc.dal.mysql.orderoperationlog;

import java.util.List;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.zc.controller.admin.orderoperationlog.vo.ZcOrderOperationLogPageReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.orderoperationlog.ZcOrderOperationLogDO;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.ibatis.annotations.Mapper;

/**
 * 销售订单操作记录 Mapper
 *
 * @author 01Coder
 */
@Mapper
public interface ZcOrderOperationLogMapper extends BaseMapperX<ZcOrderOperationLogDO> {

    /**
     * 查询指定订单的所有操作记录，按主键升序（时间线顺序）
     *
     * @param orderId 销售订单 ID
     * @return 操作记录列表
     */
    default List<ZcOrderOperationLogDO> selectListByOrderId(Long orderId) {
        return selectList(Wrappers.<ZcOrderOperationLogDO>lambdaQuery()
                .eq(ZcOrderOperationLogDO::getOrderId, orderId)
                .orderByAsc(ZcOrderOperationLogDO::getId));
    }

    /**
     * 分页查询操作记录，支持按订单、对象类型、对象 ID、操作类型过滤
     *
     * @param reqVO 分页查询条件
     * @return 分页结果
     */
    default PageResult<ZcOrderOperationLogDO> selectPage(ZcOrderOperationLogPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ZcOrderOperationLogDO>()
                .eqIfPresent(ZcOrderOperationLogDO::getOrderId, reqVO.getOrderId())
                .eqIfPresent(ZcOrderOperationLogDO::getTargetType, reqVO.getTargetType())
                .eqIfPresent(ZcOrderOperationLogDO::getTargetId, reqVO.getTargetId())
                .eqIfPresent(ZcOrderOperationLogDO::getOperateType, reqVO.getOperateType())
                .orderByAsc(ZcOrderOperationLogDO::getId));
    }

}
