package cn.iocoder.yudao.module.zc.dal.mysql.supplier;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.supplier.ZcSupplierDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.zc.controller.admin.supplier.vo.*;

/**
 * 供应商 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface ZcSupplierMapper extends BaseMapperX<ZcSupplierDO> {

    default PageResult<ZcSupplierDO> selectPage(ZcSupplierPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ZcSupplierDO>()
                .likeIfPresent(ZcSupplierDO::getShortName, reqVO.getShortName())
                .likeIfPresent(ZcSupplierDO::getName, reqVO.getName())
                .orderByDesc(ZcSupplierDO::getId));
    }

}