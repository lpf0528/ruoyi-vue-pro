package cn.iocoder.yudao.module.quiz.service.projectcategory;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.quiz.controller.admin.projectcategory.vo.*;
import cn.iocoder.yudao.module.quiz.dal.dataobject.projectcategory.QuizQuizProjectCategoryDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.quiz.dal.mysql.projectcategory.QuizQuizProjectCategoryMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.quiz.enums.ErrorCodeConstants.*;

/**
 * 项目分类 Service 实现类
 *
 * @author 01Coder
 */
@Service
@Validated
public class QuizQuizProjectCategoryServiceImpl implements QuizQuizProjectCategoryService {

    @Resource
    private QuizQuizProjectCategoryMapper quizProjectCategoryMapper;

    @Override
    public Long createQuizProjectCategory(QuizQuizProjectCategorySaveReqVO createReqVO) {
        // 插入
        QuizQuizProjectCategoryDO quizProjectCategory = BeanUtils.toBean(createReqVO, QuizQuizProjectCategoryDO.class);
        quizProjectCategoryMapper.insert(quizProjectCategory);

        // 返回
        return quizProjectCategory.getId();
    }

    @Override
    public void updateQuizProjectCategory(QuizQuizProjectCategorySaveReqVO updateReqVO) {
        // 校验存在
        validateQuizProjectCategoryExists(updateReqVO.getId());
        // 更新
        QuizQuizProjectCategoryDO updateObj = BeanUtils.toBean(updateReqVO, QuizQuizProjectCategoryDO.class);
        quizProjectCategoryMapper.updateById(updateObj);
    }

    @Override
    public void deleteQuizProjectCategory(Long id) {
        // 校验存在
        validateQuizProjectCategoryExists(id);
        // 删除
        quizProjectCategoryMapper.deleteById(id);
    }

    @Override
        public void deleteQuizProjectCategoryListByIds(List<Long> ids) {
        // 删除
        quizProjectCategoryMapper.deleteByIds(ids);
        }


    private void validateQuizProjectCategoryExists(Long id) {
        if (quizProjectCategoryMapper.selectById(id) == null) {
            throw exception(QUIZ_PROJECT_CATEGORY_NOT_EXISTS);
        }
    }

    @Override
    public QuizQuizProjectCategoryDO getQuizProjectCategory(Long id) {
        return quizProjectCategoryMapper.selectById(id);
    }

    @Override
    public PageResult<QuizQuizProjectCategoryDO> getQuizProjectCategoryPage(QuizQuizProjectCategoryPageReqVO pageReqVO) {
        return quizProjectCategoryMapper.selectPage(pageReqVO);
    }

}