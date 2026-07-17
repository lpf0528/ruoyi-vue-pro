package cn.iocoder.yudao.module.quiz.dal.mysql.projectcategory;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.quiz.dal.dataobject.projectcategory.QuizProjectCategoryDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.quiz.controller.admin.projectcategory.vo.*;

/**
 * 项目分类 Mapper
 *
 * @author 01Coder
 */
@Mapper
public interface QuizProjectCategoryMapper extends BaseMapperX<QuizProjectCategoryDO> {

    default PageResult<QuizProjectCategoryDO> selectPage(QuizProjectCategoryPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<QuizProjectCategoryDO>()
                .likeIfPresent(QuizProjectCategoryDO::getName, reqVO.getName())
                .eqIfPresent(QuizProjectCategoryDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(QuizProjectCategoryDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(QuizProjectCategoryDO::getId));
    }

}