package cn.iocoder.yudao.module.zc.dal.mysql.salesorder;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZcSalesOrderCurtainDO;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.*;

/**
 * 成品订单-窗帘行 Mapper
 *
 * @author o1Coder
 */
@Mapper
public interface ZcSalesOrderCurtainMapper extends BaseMapperX<ZcSalesOrderCurtainDO> {

    default PageResult<ZcSalesOrderCurtainDO> selectPage(ZcSalesOrderCurtainPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ZcSalesOrderCurtainDO>()
                .eqIfPresent(ZcSalesOrderCurtainDO::getOrderId, reqVO.getOrderId())
                .eqIfPresent(ZcSalesOrderCurtainDO::getCurtainId, reqVO.getCurtainId())
                .betweenIfPresent(ZcSalesOrderCurtainDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ZcSalesOrderCurtainDO::getId));
    }

    /**
     * 查询指定订单下的所有窗帘行，按主键升序排列（保持录入顺序）
     *
     * @param orderId 销售订单 ID
     * @return 窗帘行列表
     */
    default List<ZcSalesOrderCurtainDO> selectListByOrderId(Long orderId) {
        return selectList(new LambdaQueryWrapperX<ZcSalesOrderCurtainDO>()
                .eqIfPresent(ZcSalesOrderCurtainDO::getOrderId, orderId)
                .orderByAsc(ZcSalesOrderCurtainDO::getId));
    }

    /** 删除指定订单下的所有窗帘行（用于删除订单时级联清理） */
    default void deleteByOrderId(Long orderId) {
        delete(Wrappers.<ZcSalesOrderCurtainDO>lambdaQuery()
                .eq(ZcSalesOrderCurtainDO::getOrderId, orderId));
    }

    /** 批量删除多个订单下的所有窗帘行（用于批量删除订单时级联清理） */
    default void deleteByOrderIds(List<Long> orderIds) {
        delete(Wrappers.<ZcSalesOrderCurtainDO>lambdaQuery()
                .in(ZcSalesOrderCurtainDO::getOrderId, orderIds));
    }

}