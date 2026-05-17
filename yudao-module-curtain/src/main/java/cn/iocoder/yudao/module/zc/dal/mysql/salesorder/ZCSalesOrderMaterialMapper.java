package cn.iocoder.yudao.module.zc.dal.mysql.salesorder;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.salesorder.ZCSalesOrderMaterialDO;
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

}