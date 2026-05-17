package cn.iocoder.yudao.module.zc.dal.mysql.salesorder;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZcSalesOrderDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo.*;

/**
 * 销售订单 Mapper
 *
 * @author 01Coder
 */
@Mapper
public interface ZcSalesOrderMapper extends BaseMapperX<ZcSalesOrderDO> {

    default PageResult<ZcSalesOrderDO> selectPage(ZcSalesOrderPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ZcSalesOrderDO>()
                .eqIfPresent(ZcSalesOrderDO::getOrderNo, reqVO.getOrderNo())
                .eqIfPresent(ZcSalesOrderDO::getCustomerId, reqVO.getCustomerId())
                .eqIfPresent(ZcSalesOrderDO::getMobile, reqVO.getMobile())
                .eqIfPresent(ZcSalesOrderDO::getBrandId, reqVO.getBrandId())
                .betweenIfPresent(ZcSalesOrderDO::getOrderDate, reqVO.getOrderDate())
                .eqIfPresent(ZcSalesOrderDO::getLogisticId, reqVO.getLogisticId())
                .eqIfPresent(ZcSalesOrderDO::getReceiver, reqVO.getReceiver())
                .eqIfPresent(ZcSalesOrderDO::getDeliveryAddress, reqVO.getDeliveryAddress())
                .eqIfPresent(ZcSalesOrderDO::getFreight, reqVO.getFreight())
                .eqIfPresent(ZcSalesOrderDO::getTypes, reqVO.getTypes())
                .betweenIfPresent(ZcSalesOrderDO::getDeliveryDate, reqVO.getDeliveryDate())
                .eqIfPresent(ZcSalesOrderDO::getPayStatus, reqVO.getPayStatus())
                .eqIfPresent(ZcSalesOrderDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(ZcSalesOrderDO::getConfirmTime, reqVO.getConfirmTime())
                .eqIfPresent(ZcSalesOrderDO::getIsExpedited, reqVO.getIsExpedited())
                .eqIfPresent(ZcSalesOrderDO::getNote, reqVO.getNote())
                .betweenIfPresent(ZcSalesOrderDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ZcSalesOrderDO::getId));
    }

}