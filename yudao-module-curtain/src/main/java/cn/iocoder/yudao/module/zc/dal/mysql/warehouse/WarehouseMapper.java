package cn.iocoder.yudao.module.zc.dal.mysql.warehouse;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.warehouse.WarehouseDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.zc.controller.admin.warehouse.vo.*;

/**
 * 仓库 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface WarehouseMapper extends BaseMapperX<WarehouseDO> {

    default PageResult<WarehouseDO> selectPage(WarehousePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<WarehouseDO>()
                .likeIfPresent(WarehouseDO::getName, reqVO.getName())
                .eqIfPresent(WarehouseDO::getManagerId, reqVO.getManagerId())
                .eqIfPresent(WarehouseDO::getNote, reqVO.getNote())
                .betweenIfPresent(WarehouseDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(WarehouseDO::getId));
    }

}