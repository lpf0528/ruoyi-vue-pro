package cn.iocoder.yudao.module.zc.dal.mysql.warehouse;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.warehouse.ZcWarehouseDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.zc.controller.admin.warehouse.vo.*;

/**
 * 仓库 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface ZcWarehouseMapper extends BaseMapperX<ZcWarehouseDO> {

    default PageResult<ZcWarehouseDO> selectPage(ZcWarehousePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ZcWarehouseDO>()
                .likeIfPresent(ZcWarehouseDO::getName, reqVO.getName())
                .eqIfPresent(ZcWarehouseDO::getManagerId, reqVO.getManagerId())
                .orderByDesc(ZcWarehouseDO::getId));
    }

}