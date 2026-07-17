package cn.iocoder.yudao.module.quiz.controller.admin.project;

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

import cn.iocoder.yudao.module.quiz.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.quiz.dal.dataobject.project.QuizProjectDO;
import cn.iocoder.yudao.module.quiz.service.project.QuizProjectService;

@Tag(name = "管理后台 - 项目")
@RestController
@RequestMapping("/quiz/project")
@Validated
public class QuizProjectController {

    @Resource
    private QuizProjectService projectService;

    @PostMapping("/create")
    @Operation(summary = "创建项目")
    @PreAuthorize("@ss.hasPermission('quiz:project:create')")
    public CommonResult<Long> createProject(@Valid @RequestBody QuizProjectSaveReqVO createReqVO) {
        return success(projectService.createProject(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新项目")
    @PreAuthorize("@ss.hasPermission('quiz:project:update')")
    public CommonResult<Boolean> updateProject(@Valid @RequestBody QuizProjectSaveReqVO updateReqVO) {
        projectService.updateProject(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除项目")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('quiz:project:delete')")
    public CommonResult<Boolean> deleteProject(@RequestParam("id") Long id) {
        projectService.deleteProject(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除项目")
                @PreAuthorize("@ss.hasPermission('quiz:project:delete')")
    public CommonResult<Boolean> deleteProjectList(@RequestParam("ids") List<Long> ids) {
        projectService.deleteProjectListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得项目")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('quiz:project:query')")
    public CommonResult<QuizProjectRespVO> getProject(@RequestParam("id") Long id) {
        QuizProjectDO project = projectService.getProject(id);
        return success(BeanUtils.toBean(project, QuizProjectRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得项目分页")
    @PreAuthorize("@ss.hasPermission('quiz:project:query')")
    public CommonResult<PageResult<QuizProjectRespVO>> getProjectPage(@Valid QuizProjectPageReqVO pageReqVO) {
        PageResult<QuizProjectDO> pageResult = projectService.getProjectPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, QuizProjectRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出项目 Excel")
    @PreAuthorize("@ss.hasPermission('quiz:project:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportProjectExcel(@Valid QuizProjectPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<QuizProjectDO> list = projectService.getProjectPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "项目.xls", "数据", QuizProjectRespVO.class,
                        BeanUtils.toBean(list, QuizProjectRespVO.class));
    }

}