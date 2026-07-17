package cn.iocoder.yudao.module.quiz.dal.mysql.projectcategory;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.quiz.dal.dataobject.projectcategory.QuizQuizProjectCategoryDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.quiz.controller.admin.projectcategory.vo.*;

/**
 * 项目分类 Mapper
 *
 * @author 01Coder
 */
@Mapper
public interface QuizQuizProjectCategoryMapper extends BaseMapperX<QuizQuizProjectCategoryDO> {

    default PageResult<QuizQuizProjectCategoryDO> selectPage(QuizQuizProjectCategoryPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<QuizQuizProjectCategoryDO>()
                .likeIfPresent(QuizQuizProjectCategoryDO::getName, reqVO.getName())
                .eqIfPresent(QuizQuizProjectCategoryDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(QuizQuizProjectCategoryDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(QuizQuizProjectCategoryDO::getId));
    }

}