package cn.iocoder.yudao.module.zc.controller.admin.curtaintemplate;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.constraints.*;
import javax.validation.*;
import javax.servlet.http.*;
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

import cn.iocoder.yudao.module.zc.controller.admin.curtaintemplate.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtaintemplate.ZcCurtainTemplateDO;
import cn.iocoder.yudao.module.zc.service.curtaintemplate.ZcCurtainTemplateService;

@Tag(name = "管理后台 - 窗帘模板")
@RestController
@RequestMapping("/zc/curtain-template")
@Validated
public class ZcCurtainTemplateController {

    @Resource
    private ZcCurtainTemplateService curtainTemplateService;

    @PostMapping("/create")
    @Operation(summary = "创建窗帘模板")
    @PreAuthorize("@ss.hasPermission('zc:curtain-template:create')")
    public CommonResult<Long> createCurtainTemplate(@Valid @RequestBody ZcCurtainTemplateSaveReqVO createReqVO) {
        return success(curtainTemplateService.createCurtainTemplate(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新窗帘模板")
    @PreAuthorize("@ss.hasPermission('zc:curtain-template:update')")
    public CommonResult<Boolean> updateCurtainTemplate(@Valid @RequestBody ZcCurtainTemplateSaveReqVO updateReqVO) {
        curtainTemplateService.updateCurtainTemplate(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除窗帘模板")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('zc:curtain-template:delete')")
    public CommonResult<Boolean> deleteCurtainTemplate(@RequestParam("id") Long id) {
        curtainTemplateService.deleteCurtainTemplate(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除窗帘模板")
                @PreAuthorize("@ss.hasPermission('zc:curtain-template:delete')")
    public CommonResult<Boolean> deleteCurtainTemplateList(@RequestParam("ids") List<Long> ids) {
        curtainTemplateService.deleteCurtainTemplateListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得窗帘模板")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('zc:curtain-template:query')")
    public CommonResult<ZcCurtainTemplateRespVO> getCurtainTemplate(@RequestParam("id") Long id) {
        ZcCurtainTemplateDO curtainTemplate = curtainTemplateService.getCurtainTemplate(id);
        return success(BeanUtils.toBean(curtainTemplate, ZcCurtainTemplateRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得窗帘模板分页")
    @PreAuthorize("@ss.hasPermission('zc:curtain-template:query')")
    public CommonResult<PageResult<ZcCurtainTemplateRespVO>> getCurtainTemplatePage(@Valid ZcCurtainTemplatePageReqVO pageReqVO) {
        return success(curtainTemplateService.getCurtainTemplatePage(pageReqVO));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出窗帘模板 Excel")
    @PreAuthorize("@ss.hasPermission('zc:curtain-template:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportCurtainTemplateExcel(@Valid ZcCurtainTemplatePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ZcCurtainTemplateRespVO> list = curtainTemplateService.getCurtainTemplatePage(pageReqVO).getList();
        ExcelUtils.write(response, "窗帘模板.xls", "数据", ZcCurtainTemplateRespVO.class, list);
    }

}