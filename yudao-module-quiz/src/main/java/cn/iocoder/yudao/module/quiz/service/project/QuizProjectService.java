package cn.iocoder.yudao.module.quiz.service.project;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.quiz.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.quiz.dal.dataobject.project.QuizProjectDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 项目 Service 接口
 *
 * @author 01Coder
 */
public interface QuizProjectService {

    /**
     * 创建项目
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProject(@Valid QuizProjectSaveReqVO createReqVO);

    /**
     * 更新项目
     *
     * @param updateReqVO 更新信息
     */
    void updateProject(@Valid QuizProjectSaveReqVO updateReqVO);

    /**
     * 删除项目
     *
     * @param id 编号
     */
    void deleteProject(Long id);

    /**
    * 批量删除项目
    *
    * @param ids 编号
    */
    void deleteProjectListByIds(List<Long> ids);

    /**
     * 获得项目
     *
     * @param id 编号
     * @return 项目
     */
    QuizProjectDO getProject(Long id);

    /**
     * 获得项目分页
     *
     * @param pageReqVO 分页查询
     * @return 项目分页
     */
    PageResult<QuizProjectDO> getProjectPage(QuizProjectPageReqVO pageReqVO);

}