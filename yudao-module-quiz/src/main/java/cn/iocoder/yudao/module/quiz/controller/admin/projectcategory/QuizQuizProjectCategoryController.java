package cn.iocoder.yudao.module.quiz.controller.admin.projectcategory;

import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.constraints.*;
import jakarta.validation.*;
import jakarta.servlet.http.*;
import java.util.*;
import java.io.IOException;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.quiz.controller.admin.projectcategory.vo.*;
import cn.iocoder.yudao.module.quiz.dal.dataobject.projectcategory.QuizQuizProjectCategoryDO;
import cn.iocoder.yudao.module.quiz.service.projectcategory.QuizQuizProjectCategoryService;

@Tag(name = "管理后台 - 项目分类")
@RestController
@RequestMapping("/quiz/quiz-project-category")
@Validated
public class QuizQuizProjectCategoryController {

    @Resource
    private QuizQuizProjectCategoryService quizProjectCategoryService;

    @PostMapping("/create")
    @Operation(summary = "创建项目分类")
    @PreAuthorize("@ss.hasPermission('quiz:quiz-project-category:create')")
    public CommonResult<Long> createQuizProjectCategory(@Valid @RequestBody QuizQuizProjectCategorySaveReqVO createReqVO) {
        return success(quizProjectCategoryService.createQuizProjectCategory(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新项目分类")
    @PreAuthorize("@ss.hasPermission('quiz:quiz-project-category:update')")
    public CommonResult<Boolean> updateQuizProjectCategory(@Valid @RequestBody QuizQuizProjectCategorySaveReqVO updateReqVO) {
        quizProjectCategoryService.updateQuizProjectCategory(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除项目分类")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('quiz:quiz-project-category:delete')")
    public CommonResult<Boolean> deleteQuizProjectCategory(@RequestParam("id") Long id) {
        quizProjectCategoryService.deleteQuizProjectCategory(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除项目分类")
                @PreAuthorize("@ss.hasPermission('quiz:quiz-project-category:delete')")
    public CommonResult<Boolean> deleteQuizProjectCategoryList(@RequestParam("ids") List<Long> ids) {
        quizProjectCategoryService.deleteQuizProjectCategoryListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得项目分类")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('quiz:quiz-project-category:query')")
    public CommonResult<QuizQuizProjectCategoryRespVO> getQuizProjectCategory(@RequestParam("id") Long id) {
        QuizQuizProjectCategoryDO quizProjectCategory = quizProjectCategoryService.getQuizProjectCategory(id);
        return success(BeanUtils.toBean(quizProjectCategory, QuizQuizProjectCategoryRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得项目分类分页")
    @PreAuthorize("@ss.hasPermission('quiz:quiz-project-category:query')")
    public CommonResult<PageResult<QuizQuizProjectCategoryRespVO>> getQuizProjectCategoryPage(@Valid QuizQuizProjectCategoryPageReqVO pageReqVO) {
        PageResult<QuizQuizProjectCategoryDO> pageResult = quizProjectCategoryService.getQuizProjectCategoryPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, QuizQuizProjectCategoryRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出项目分类 Excel")
    @PreAuthorize("@ss.hasPermission('quiz:quiz-project-category:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportQuizProjectCategoryExcel(@Valid QuizQuizProjectCategoryPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<QuizQuizProjectCategoryDO> list = quizProjectCategoryService.getQuizProjectCategoryPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "项目分类.xls", "数据", QuizQuizProjectCategoryRespVO.class,
                        BeanUtils.toBean(list, QuizQuizProjectCategoryRespVO.class));
    }

}
