package cn.iocoder.yudao.module.zc.dal.mysql.sale;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.zc.controller.admin.vo.sales.ZcSalesOrderPageReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.sale.ZcSalesOrderDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ZcSalesOrderMapper extends BaseMapperX<ZcSalesOrderDO> {

    default PageResult<ZcSalesOrderDO> selectPage(ZcSalesOrderPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ZcSalesOrderDO>()
                .likeIfPresent(ZcSalesOrderDO::getOrderNo, reqVO.getOrderNo())
                .eqIfPresent(ZcSalesOrderDO::getCustomerId, reqVO.getCustomerId())
                .eqIfPresent(ZcSalesOrderDO::getTypes, reqVO.getTypes())
                .eqIfPresent(ZcSalesOrderDO::getPayStatus, reqVO.getPayStatus())
                .eqIfPresent(ZcSalesOrderDO::getConfirmStatus, reqVO.getConfirmStatus())
                .orderByDesc(ZcSalesOrderDO::getId));
    }

}
