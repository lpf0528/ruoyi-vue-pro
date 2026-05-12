package cn.iocoder.yudao.module.zc.dal.mysql.curtaintemplate;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtaintemplate.CurtainTemplateDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.zc.controller.admin.curtaintemplate.vo.*;

/**
 * 窗帘模板 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface CurtainTemplateMapper extends BaseMapperX<CurtainTemplateDO> {

    default PageResult<CurtainTemplateDO> selectPage(CurtainTemplatePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CurtainTemplateDO>()
                .eqIfPresent(CurtainTemplateDO::getCurtainId, reqVO.getCurtainId())
                .eqIfPresent(CurtainTemplateDO::getStructureId, reqVO.getStructureId())
                .eqIfPresent(CurtainTemplateDO::getElementId, reqVO.getElementId())
                .eqIfPresent(CurtainTemplateDO::getUnitId, reqVO.getUnitId())
                .betweenIfPresent(CurtainTemplateDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(CurtainTemplateDO::getId));
    }

}