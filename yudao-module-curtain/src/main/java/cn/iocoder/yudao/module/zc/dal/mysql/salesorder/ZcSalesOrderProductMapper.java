package cn.iocoder.yudao.module.zc.dal.mysql.salesorder;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZcSalesOrderProductDO;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.ibatis.annotations.Mapper;

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

}
