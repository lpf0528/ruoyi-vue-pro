package cn.iocoder.yudao.module.zc.dal.mysql.curtaintemplate;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtaintemplate.ZcCurtainTemplateDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.zc.controller.admin.curtaintemplate.vo.*;

/**
 * 窗帘模板 Mapper
 *
 * @author 01Coder
 */
@Mapper
public interface ZcCurtainTemplateMapper extends BaseMapperX<ZcCurtainTemplateDO> {

    default PageResult<ZcCurtainTemplateDO> selectPage(ZcCurtainTemplatePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ZcCurtainTemplateDO>()
                .eqIfPresent(ZcCurtainTemplateDO::getCurtainId, reqVO.getCurtainId())
                .eqIfPresent(ZcCurtainTemplateDO::getStructureId, reqVO.getStructureId())
                .eqIfPresent(ZcCurtainTemplateDO::getElementId, reqVO.getElementId())
                .eqIfPresent(ZcCurtainTemplateDO::getUnitId, reqVO.getUnitId())
                .orderByDesc(ZcCurtainTemplateDO::getId));
    }

}