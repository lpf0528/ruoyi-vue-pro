package cn.iocoder.yudao.module.zc.dal.mysql.curtainpleatratio;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtainpleatratio.CurtainPleatRatioDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.zc.controller.admin.curtainpleatratio.vo.*;

/**
 * 褶倍 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface CurtainPleatRatioMapper extends BaseMapperX<CurtainPleatRatioDO> {

    default PageResult<CurtainPleatRatioDO> selectPage(CurtainPleatRatioPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CurtainPleatRatioDO>()
                .eqIfPresent(CurtainPleatRatioDO::getValue, reqVO.getValue())
                .eqIfPresent(CurtainPleatRatioDO::getRank, reqVO.getRank())
                .betweenIfPresent(CurtainPleatRatioDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(CurtainPleatRatioDO::getId));
    }

}