package cn.iocoder.yudao.module.quiz.dal.mysql.project;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.quiz.dal.dataobject.project.QuizProjectDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.quiz.controller.admin.project.vo.*;

/**
 * 项目 Mapper
 *
 * @author 01Coder
 */
@Mapper
public interface QuizProjectMapper extends BaseMapperX<QuizProjectDO> {

    default PageResult<QuizProjectDO> selectPage(QuizProjectPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<QuizProjectDO>()
                .likeIfPresent(QuizProjectDO::getName, reqVO.getName())
                .eqIfPresent(QuizProjectDO::getStatus, reqVO.getStatus())
                .eqIfPresent(QuizProjectDO::getRecommendHot, reqVO.getRecommendHot())
                .eqIfPresent(QuizProjectDO::getRecommendBanner, reqVO.getRecommendBanner())
                .betweenIfPresent(QuizProjectDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(QuizProjectDO::getId));
    }

}