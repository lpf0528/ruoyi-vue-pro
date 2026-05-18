package cn.iocoder.yudao.module.zc.dal.mysql.salesorder;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZcSalesOrderStructureDO;
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

}