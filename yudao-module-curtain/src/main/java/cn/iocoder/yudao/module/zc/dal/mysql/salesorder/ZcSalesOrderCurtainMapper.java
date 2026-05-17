package cn.iocoder.yudao.module.zc.dal.mysql.salesorder;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZcSalesOrderCurtainDO;
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

}