package cn.iocoder.yudao.module.zc.dal.mysql.curtainpleatratio;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtainpleatratio.ZcCurtainPleatRatioDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.zc.controller.admin.curtainpleatratio.vo.*;

/**
 * 褶倍 Mapper
 *
 * @author 01Coder
 */
@Mapper
public interface ZcCurtainPleatRatioMapper extends BaseMapperX<ZcCurtainPleatRatioDO> {

    default PageResult<ZcCurtainPleatRatioDO> selectPage(ZcCurtainPleatRatioPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ZcCurtainPleatRatioDO>()
                .eqIfPresent(ZcCurtainPleatRatioDO::getValue, reqVO.getValue())
                .orderByDesc(ZcCurtainPleatRatioDO::getId));
    }

    default List<ZcCurtainPleatRatioDO> selectList(ZcCurtainPleatRatioListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<ZcCurtainPleatRatioDO>()
                .orderByDesc(ZcCurtainPleatRatioDO::getId));
    }

}