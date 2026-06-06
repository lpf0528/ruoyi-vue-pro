package cn.iocoder.yudao.module.zc.dal.mysql.salesorder;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.ZcSalesOrderProductLineRespVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZcSalesOrderProductDO;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 销售订单-产品行 Mapper
 *
 * @author 01Coder
 */
@Mapper
public interface ZcSalesOrderProductMapper extends BaseMapperX<ZcSalesOrderProductDO> {

    /**
     * 查询指定订单下的所有产品行，按主键升序排列
     *
     * @param orderId 销售订单 ID
     * @return 产品行列表
     */
    default List<ZcSalesOrderProductDO> selectListByOrderId(Long orderId) {
        return selectList(new LambdaQueryWrapperX<ZcSalesOrderProductDO>()
                .eq(ZcSalesOrderProductDO::getOrderId, orderId)
                .orderByAsc(ZcSalesOrderProductDO::getId));
    }

    /**
     * 按订单 ID 查询产品行列表（JOIN 产品表取名称、JOIN 批次表取批次号），结果直接映射到 VO
     *
     * @param orderId 销售订单 ID
     * @return 含产品名称、批次号的产品行 VO 列表
     */
    List<ZcSalesOrderProductLineRespVO> selectProductLinesWithVOByOrderId(@Param("orderId") Long orderId);

    /** 删除指定订单下的所有产品行（用于删除订单时级联清理） */
    default void deleteByOrderId(Long orderId) {
        delete(Wrappers.<ZcSalesOrderProductDO>lambdaQuery()
                .eq(ZcSalesOrderProductDO::getOrderId, orderId));
    }

    /** 批量删除多个订单下的所有产品行 */
    default void deleteByOrderIds(List<Long> orderIds) {
        delete(Wrappers.<ZcSalesOrderProductDO>lambdaQuery()
                .in(ZcSalesOrderProductDO::getOrderId, orderIds));
    }

    /**
     * 批量更新指定订单下所有产品行的状态
     *
     * <p>用于确认/取消确认面料单时，将产品行状态与主订单状态保持同步</p>
     *
     * @param orderId 销售订单 ID
     * @param status  目标状态，参见 {@link cn.iocoder.yudao.module.zc.enums.ZcSalesOrderStatusEnum}
     */
    default void updateStatusByOrderId(Long orderId, String status) {
        update(null, Wrappers.<ZcSalesOrderProductDO>lambdaUpdate()
                .set(ZcSalesOrderProductDO::getStatus, status)
                .eq(ZcSalesOrderProductDO::getOrderId, orderId));
    }

}
