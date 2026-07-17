package cn.iocoder.yudao.module.quiz.service.projectcategory;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.quiz.controller.admin.projectcategory.vo.*;
import cn.iocoder.yudao.module.quiz.dal.dataobject.projectcategory.QuizProjectCategoryDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 项目分类 Service 接口
 *
 * @author 01Coder
 */
public interface QuizProjectCategoryService {

    /**
     * 创建项目分类
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProjectCategory(@Valid QuizProjectCategorySaveReqVO createReqVO);

    /**
     * 更新项目分类
     *
     * @param updateReqVO 更新信息
     */
    void updateProjectCategory(@Valid QuizProjectCategorySaveReqVO updateReqVO);

    /**
     * 删除项目分类
     *
     * @param id 编号
     */
    void deleteProjectCategory(Long id);

    /**
    * 批量删除项目分类
    *
    * @param ids 编号
    */
    void deleteProjectCategoryListByIds(List<Long> ids);

    /**
     * 获得项目分类
     *
     * @param id 编号
     * @return 项目分类
     */
    QuizProjectCategoryDO getProjectCategory(Long id);

    /**
     * 获得项目分类分页
     *
     * @param pageReqVO 分页查询
     * @return 项目分类分页
     */
    PageResult<QuizProjectCategoryDO> getProjectCategoryPage(QuizProjectCategoryPageReqVO pageReqVO);

}