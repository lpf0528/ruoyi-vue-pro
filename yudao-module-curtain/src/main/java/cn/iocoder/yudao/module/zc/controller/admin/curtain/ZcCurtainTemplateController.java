package cn.iocoder.yudao.module.zc.controller.admin.curtain;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.zc.service.curtain.ZcCurtainTemplateService;
import cn.iocoder.yudao.module.zc.controller.admin.vo.curtain.ZcCurtainTemplatePageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.curtain.ZcCurtainTemplateSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtain.ZcCurtainTemplateDO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 智仓窗帘模板")
@RestController
@RequestMapping("/zc/curtain-template")
@Validated
public class ZcCurtainTemplateController {

    @Resource
    private ZcCurtainTemplateService curtainTemplateService;

    @PostMapping("/create")
    @Operation(summary = "创建窗帘模板")
    @PreAuthorize("@ss.hasPermission('zc:curtain-template:create')")
    public CommonResult<Long> create(@Valid @RequestBody ZcCurtainTemplateSaveReqVO reqVO) {
        return success(curtainTemplateService.create(reqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新窗帘模板")
    @PreAuthorize("@ss.hasPermission('zc:curtain-template:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody ZcCurtainTemplateSaveReqVO reqVO) {
        curtainTemplateService.update(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除窗帘模板")
    @PreAuthorize("@ss.hasPermission('zc:curtain-template:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        curtainTemplateService.delete(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得窗帘模板")
    @PreAuthorize("@ss.hasPermission('zc:curtain-template:query')")
    public CommonResult<ZcCurtainTemplateSaveReqVO> get(@RequestParam("id") Long id) {
        return success(BeanUtils.toBean(curtainTemplateService.get(id), ZcCurtainTemplateSaveReqVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "窗帘模板分页")
    @PreAuthorize("@ss.hasPermission('zc:curtain-template:query')")
    public CommonResult<PageResult<ZcCurtainTemplateSaveReqVO>> page(@Valid ZcCurtainTemplatePageReqVO pageReqVO) {
        PageResult<ZcCurtainTemplateDO> pageResult = curtainTemplateService.getPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ZcCurtainTemplateSaveReqVO.class));
    }

}
