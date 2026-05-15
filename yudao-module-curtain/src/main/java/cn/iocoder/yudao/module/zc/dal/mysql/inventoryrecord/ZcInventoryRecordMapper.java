package cn.iocoder.yudao.module.zc.dal.mysql.inventoryrecord;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.inventoryrecord.ZcInventoryRecordDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.zc.controller.admin.inventoryrecord.vo.*;

/**
 * 盘点记录 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface ZcInventoryRecordMapper extends BaseMapperX<ZcInventoryRecordDO> {

    default PageResult<ZcInventoryRecordDO> selectPage(ZcInventoryRecordPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ZcInventoryRecordDO>()
                .eqIfPresent(ZcInventoryRecordDO::getProductId, reqVO.getProductId())
                .eqIfPresent(ZcInventoryRecordDO::getBatchId, reqVO.getBatchId())
                .betweenIfPresent(ZcInventoryRecordDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ZcInventoryRecordDO::getId));
    }

}