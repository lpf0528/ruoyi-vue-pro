package cn.iocoder.yudao.module.zc.dal.mysql.salesorder;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZCSalesOrderMaterialDO;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.*;

/**
 * 成品订单-用料明细 Mapper
 *
 * @author 01Coder
 */
@Mapper
public interface ZCSalesOrderMaterialMapper extends BaseMapperX<ZCSalesOrderMaterialDO> {

    default PageResult<ZCSalesOrderMaterialDO> selectPage(ZCSalesOrderMaterialPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ZCSalesOrderMaterialDO>()
                .eqIfPresent(ZCSalesOrderMaterialDO::getOrderId, reqVO.getOrderId())
                .eqIfPresent(ZCSalesOrderMaterialDO::getOrderStructureId, reqVO.getOrderStructureId())
                .orderByDesc(ZCSalesOrderMaterialDO::getId));
    }

    /**
     * 查询指定订单下的所有用料明细，按主键升序排列（保持录入顺序）
     *
     * @param orderId 销售订单 ID
     * @return 用料明细列表
     */
    default List<ZCSalesOrderMaterialDO> selectListByOrderId(Long orderId) {
        return selectList(new LambdaQueryWrapperX<ZCSalesOrderMaterialDO>()
                .eqIfPresent(ZCSalesOrderMaterialDO::getOrderId, orderId)
                .orderByAsc(ZCSalesOrderMaterialDO::getId));
    }

    /** 删除指定订单下的所有用料明细（用于删除订单时级联清理） */
    default void deleteByOrderId(Long orderId) {
        delete(Wrappers.<ZCSalesOrderMaterialDO>lambdaQuery()
                .eq(ZCSalesOrderMaterialDO::getOrderId, orderId));
    }

    /** 批量删除多个订单下的所有用料明细（用于批量删除订单时级联清理） */
    default void deleteByOrderIds(List<Long> orderIds) {
        delete(Wrappers.<ZCSalesOrderMaterialDO>lambdaQuery()
                .in(ZCSalesOrderMaterialDO::getOrderId, orderIds));
    }

    /** 统计指定批次被订单用料明细引用的数量 */
    default long countByBatchId(Long batchId) {
        return selectCount(new LambdaQueryWrapperX<ZCSalesOrderMaterialDO>()
                .eq(ZCSalesOrderMaterialDO::getBatchId, batchId));
    }

    /**
     * 统计指定订单下处于特定状态的用料明细数量
     *
     * @param orderId 销售订单 ID
     * @param status  用料明细状态，参见 {@link cn.iocoder.yudao.module.zc.enums.ZcSalesOrderMaterialStatusEnum}
     * @return 匹配数量
     */
    default long countByOrderIdAndStatus(Long orderId, String status) {
        return selectCount(new LambdaQueryWrapperX<ZCSalesOrderMaterialDO>()
                .eq(ZCSalesOrderMaterialDO::getOrderId, orderId)
                .eq(ZCSalesOrderMaterialDO::getStatus, status));
    }

}