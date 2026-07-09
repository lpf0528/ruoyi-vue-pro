package cn.iocoder.yudao.module.zc.dal.mysql.salesorder;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZcSalesOrderStructureDO;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.*;

/**
 * 成品订单-结构 Mapper
 *
 * @author 01Coder
 */
@Mapper
public interface ZcSalesOrderStructureMapper extends BaseMapperX<ZcSalesOrderStructureDO> {

    default PageResult<ZcSalesOrderStructureDO> selectPage(ZcSalesOrderStructurePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ZcSalesOrderStructureDO>()
                .eqIfPresent(ZcSalesOrderStructureDO::getOrderId, reqVO.getOrderId())
                .eqIfPresent(ZcSalesOrderStructureDO::getOrderCurtainId, reqVO.getOrderCurtainId())
                .eqIfPresent(ZcSalesOrderStructureDO::getStructureId, reqVO.getStructureId())
                .orderByDesc(ZcSalesOrderStructureDO::getId));
    }

    /**
     * 查询指定窗帘行下的所有结构行，按主键升序排列
     *
     * @param orderCurtainId 窗帘行 ID
     * @return 结构行列表
     */
    default List<ZcSalesOrderStructureDO> selectListByOrderCurtainId(Long orderCurtainId) {
        return selectList(new LambdaQueryWrapperX<ZcSalesOrderStructureDO>()
                .eqIfPresent(ZcSalesOrderStructureDO::getOrderCurtainId, orderCurtainId)
                .orderByAsc(ZcSalesOrderStructureDO::getId));
    }

    /**
     * 查询指定订单下的所有结构行，按主键升序排列（保持录入顺序）
     *
     * @param orderId 销售订单 ID
     * @return 结构行列表
     */
    default List<ZcSalesOrderStructureDO> selectListByOrderId(Long orderId) {
        return selectList(new LambdaQueryWrapperX<ZcSalesOrderStructureDO>()
                .eqIfPresent(ZcSalesOrderStructureDO::getOrderId, orderId)
                .orderByAsc(ZcSalesOrderStructureDO::getId));
    }

    /** 删除指定订单下的所有结构行（用于删除订单时级联清理） */
    default void deleteByOrderId(Long orderId) {
        delete(Wrappers.<ZcSalesOrderStructureDO>lambdaQuery()
                .eq(ZcSalesOrderStructureDO::getOrderId, orderId));
    }

    /** 批量删除多个订单下的所有结构行（用于批量删除订单时级联清理） */
    default void deleteByOrderIds(List<Long> orderIds) {
        delete(Wrappers.<ZcSalesOrderStructureDO>lambdaQuery()
                .in(ZcSalesOrderStructureDO::getOrderId, orderIds));
    }

}