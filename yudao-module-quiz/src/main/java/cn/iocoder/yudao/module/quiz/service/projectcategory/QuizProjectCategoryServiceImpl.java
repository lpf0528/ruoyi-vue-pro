package cn.iocoder.yudao.module.quiz.service.projectcategory;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.quiz.controller.admin.projectcategory.vo.*;
import cn.iocoder.yudao.module.quiz.dal.dataobject.projectcategory.QuizProjectCategoryDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.quiz.dal.mysql.projectcategory.QuizProjectCategoryMapper;

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
public class QuizProjectCategoryServiceImpl implements QuizProjectCategoryService {

    @Resource
    private QuizProjectCategoryMapper projectCategoryMapper;

    @Override
    public Long createProjectCategory(QuizProjectCategorySaveReqVO createReqVO) {
        // 插入
        QuizProjectCategoryDO projectCategory = BeanUtils.toBean(createReqVO, QuizProjectCategoryDO.class);
        projectCategoryMapper.insert(projectCategory);

        // 返回
        return projectCategory.getId();
    }

    @Override
    public void updateProjectCategory(QuizProjectCategorySaveReqVO updateReqVO) {
        // 校验存在
        validateProjectCategoryExists(updateReqVO.getId());
        // 更新
        QuizProjectCategoryDO updateObj = BeanUtils.toBean(updateReqVO, QuizProjectCategoryDO.class);
        projectCategoryMapper.updateById(updateObj);
    }

    @Override
    public void deleteProjectCategory(Long id) {
        // 校验存在
        validateProjectCategoryExists(id);
        // 删除
        projectCategoryMapper.deleteById(id);
    }

    @Override
        public void deleteProjectCategoryListByIds(List<Long> ids) {
        // 删除
        projectCategoryMapper.deleteByIds(ids);
        }


    private void validateProjectCategoryExists(Long id) {
        if (projectCategoryMapper.selectById(id) == null) {
            throw exception(PROJECT_CATEGORY_NOT_EXISTS);
        }
    }

    @Override
    public QuizProjectCategoryDO getProjectCategory(Long id) {
        return projectCategoryMapper.selectById(id);
    }

    @Override
    public PageResult<QuizProjectCategoryDO> getProjectCategoryPage(QuizProjectCategoryPageReqVO pageReqVO) {
        return projectCategoryMapper.selectPage(pageReqVO);
    }

}