package cn.iocoder.yudao.module.quiz.service.project;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.quiz.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.quiz.dal.dataobject.project.QuizProjectDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.quiz.dal.mysql.project.QuizProjectMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.quiz.enums.ErrorCodeConstants.*;

/**
 * 项目 Service 实现类
 *
 * @author 01Coder
 */
@Service
@Validated
public class QuizProjectServiceImpl implements QuizProjectService {

    @Resource
    private QuizProjectMapper projectMapper;

    @Override
    public Long createProject(QuizProjectSaveReqVO createReqVO) {
        // 插入
        QuizProjectDO project = BeanUtils.toBean(createReqVO, QuizProjectDO.class);
        projectMapper.insert(project);

        // 返回
        return project.getId();
    }

    @Override
    public void updateProject(QuizProjectSaveReqVO updateReqVO) {
        // 校验存在
        validateProjectExists(updateReqVO.getId());
        // 更新
        QuizProjectDO updateObj = BeanUtils.toBean(updateReqVO, QuizProjectDO.class);
        projectMapper.updateById(updateObj);
    }

    @Override
    public void deleteProject(Long id) {
        // 校验存在
        validateProjectExists(id);
        // 删除
        projectMapper.deleteById(id);
    }

    @Override
        public void deleteProjectListByIds(List<Long> ids) {
        // 删除
        projectMapper.deleteByIds(ids);
        }


    private void validateProjectExists(Long id) {
        if (projectMapper.selectById(id) == null) {
            throw exception(PROJECT_NOT_EXISTS);
        }
    }

    @Override
    public QuizProjectDO getProject(Long id) {
        return projectMapper.selectById(id);
    }

    @Override
    public PageResult<QuizProjectDO> getProjectPage(QuizProjectPageReqVO pageReqVO) {
        return projectMapper.selectPage(pageReqVO);
    }

}