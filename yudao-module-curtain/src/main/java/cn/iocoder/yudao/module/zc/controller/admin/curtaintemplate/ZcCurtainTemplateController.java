package cn.iocoder.yudao.module.zc.controller.admin.curtaintemplate;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.*;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.module.zc.controller.admin.curtaintemplate.vo.*;
import cn.iocoder.yudao.module.zc.service.curtaintemplate.ZcCurtainTemplateService;

@Tag(name = "管理后台 - 窗帘模板")
@RestController
@RequestMapping("/zc/curtain-template")
@Validated
public class ZcCurtainTemplateController {

    @Resource
    private ZcCurtainTemplateService curtainTemplateService;

    @PostMapping("/create")
    @Operation(summary = "保存窗帘模板（新增或修改）")
    @PreAuthorize("@ss.hasPermission('zc:curtain-template:create')")
    public CommonResult<Boolean> saveCurtainTemplate(@Valid @RequestBody ZcCurtainTemplateSaveReqVO saveReqVO) {
        curtainTemplateService.saveCurtainTemplate(saveReqVO);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得窗帘模板")
    @Parameter(name = "curtainId", description = "款式ID", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('zc:curtain-template:query')")
    public CommonResult<ZcCurtainTemplateSaveReqVO> getCurtainTemplate(@RequestParam("curtainId") Long curtainId) {
        return success(curtainTemplateService.getCurtainTemplateByCurtainId(curtainId));
    }

}
