package cn.iocoder.yudao.module.zc.dal.mysql.curtaintemplate;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.zc.controller.admin.curtaintemplate.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtaintemplate.ZcCurtainTemplateDO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 窗帘模板 Mapper
 *
 * @author 01Coder
 */
@Mapper
public interface ZcCurtainTemplateMapper extends BaseMapperX<ZcCurtainTemplateDO> {

    IPage<ZcCurtainTemplateRespVO> selectPageWithVO(IPage<?> page, @Param("reqVO") ZcCurtainTemplatePageReqVO reqVO);

    default PageResult<ZcCurtainTemplateRespVO> selectPage(ZcCurtainTemplatePageReqVO reqVO) {
        IPage<ZcCurtainTemplateRespVO> result = selectPageWithVO(
                new Page<>(reqVO.getPageNo(), reqVO.getPageSize()), reqVO);
        return new PageResult<>(result.getRecords(), result.getTotal());
    }

}