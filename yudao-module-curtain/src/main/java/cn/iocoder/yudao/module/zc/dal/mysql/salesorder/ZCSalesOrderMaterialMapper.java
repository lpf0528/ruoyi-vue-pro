package cn.iocoder.yudao.module.zc.dal.mysql.salesorder;

import java.math.BigDecimal;
import java.util.*;

import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZCSalesOrderMaterialDO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.*;

/**
 * 成品订单-用料明细 Mapper
 *
 * @author 01Coder
 */
@Mapper
public interface ZCSalesOrderMaterialMapper extends BaseMapperX<ZCSalesOrderMaterialDO> {

    /** XML 绑定方法，联表查询销售单号/客户名称、产品名称/版本、组件名称、批次号，由分页插件注入 LIMIT/OFFSET 及 COUNT */
    IPage<ZCSalesOrderMaterialRespVO> selectPageWithVO(IPage<?> page, @Param("reqVO") ZCSalesOrderMaterialPageReqVO reqVO);

    /**
     * 按与分页相同的筛选条件，汇总全量用料合计与金额合计
     *
     * @param reqVO 分页筛选条件
     * @return 仅填充 totalQuantity、totalAmount 的合计结果
     */
    ZCSalesOrderMaterialPageRespVO selectSummary(@Param("reqVO") ZCSalesOrderMaterialPageReqVO reqVO);

    /**
     * 分页查询用料明细，并附带当前筛选条件下的用料/金额合计
     *
     * @param reqVO 分页筛选条件
     * @return 分页列表 + 全量合计
     */
    default ZCSalesOrderMaterialPageRespVO selectPage(ZCSalesOrderMaterialPageReqVO reqVO) {
        IPage<ZCSalesOrderMaterialRespVO> result = selectPageWithVO(
                new Page<>(reqVO.getPageNo(), reqVO.getPageSize()), reqVO);
        ZCSalesOrderMaterialPageRespVO summary = selectSummary(reqVO);
        if (summary == null) {
            summary = new ZCSalesOrderMaterialPageRespVO();
            summary.setTotalQuantity(BigDecimal.ZERO);
            summary.setTotalAmount(BigDecimal.ZERO);
        }
        summary.setList(result.getRecords());
        summary.setTotal(result.getTotal());
        return summary;
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
     * 查询指定结构行下的所有用料明细
     *
     * @param structureIds 结构行 ID 集合
     * @return 用料明细列表
     */
    default List<ZCSalesOrderMaterialDO> selectListByStructureIds(Collection<Long> structureIds) {
        if (structureIds == null || structureIds.isEmpty()) {
            return Collections.emptyList();
        }
        return selectList(new LambdaQueryWrapperX<ZCSalesOrderMaterialDO>()
                .in(ZCSalesOrderMaterialDO::getOrderStructureId, structureIds)
                .orderByAsc(ZCSalesOrderMaterialDO::getId));
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

    /**
     * 统计已确认订单中，各产品不同规格的用料数量合计
     *
     * @param reqVO 确认时间范围
     * @return 按 product_id + spec 分组的用料数量列表
     */
    List<ZcSalesOrderMaterialProductStatisticsRespVO> selectProductSpecStatistics(
            @Param("reqVO") ZcSalesOrderCustomerStatisticsReqVO reqVO);

}