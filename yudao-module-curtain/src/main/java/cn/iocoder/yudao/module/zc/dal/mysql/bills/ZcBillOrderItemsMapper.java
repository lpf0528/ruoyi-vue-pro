package cn.iocoder.yudao.module.zc.dal.mysql.bills;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.controller.admin.bills.vo.ZcBillOrderItemRespVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.bills.ZcBillOrderItemsDO;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 收款单订单分摊明细 Mapper
 *
 * @author 01Coder
 */
@Mapper
public interface ZcBillOrderItemsMapper extends BaseMapperX<ZcBillOrderItemsDO> {

    default List<ZcBillOrderItemsDO> selectByBillId(Long billId) {
        return selectList(Wrappers.<ZcBillOrderItemsDO>lambdaQuery()
                .eq(ZcBillOrderItemsDO::getBillId, billId));
    }

    /** 删除指定收款单下的所有分摊明细（用于删除/更新收款单时级联清理） */
    default void deleteByBillId(Long billId) {
        delete(Wrappers.<ZcBillOrderItemsDO>lambdaQuery()
                .eq(ZcBillOrderItemsDO::getBillId, billId));
    }

    /**
     * 查询指定收款单的订单分摊明细，JOIN zc_sales_order 带出订单号
     *
     * @param billId 收款单 ID
     * @return 分摊明细列表（含订单号）
     */
    List<ZcBillOrderItemRespVO> selectListWithOrderNoByBillId(@Param("billId") Long billId);

}
