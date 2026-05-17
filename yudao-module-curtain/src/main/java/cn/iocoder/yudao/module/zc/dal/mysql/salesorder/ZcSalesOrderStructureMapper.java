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

}