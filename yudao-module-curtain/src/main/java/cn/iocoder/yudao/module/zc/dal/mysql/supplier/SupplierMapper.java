package cn.iocoder.yudao.module.zc.dal.mysql.supplier;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.supplier.SupplierDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.zc.controller.admin.supplier.vo.*;

/**
 * 供应商 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface SupplierMapper extends BaseMapperX<SupplierDO> {

    default PageResult<SupplierDO> selectPage(SupplierPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SupplierDO>()
                .likeIfPresent(SupplierDO::getShortName, reqVO.getShortName())
                .likeIfPresent(SupplierDO::getName, reqVO.getName())
                .eqIfPresent(SupplierDO::getNote, reqVO.getNote())
                .betweenIfPresent(SupplierDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SupplierDO::getId));
    }

}