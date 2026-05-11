package cn.iocoder.yudao.module.zc.controller.admin.curtain;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.zc.service.curtain.ZcCurtainInstallProcessService;
import cn.iocoder.yudao.module.zc.controller.admin.vo.curtain.ZcCurtainInstallProcessPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.curtain.ZcCurtainInstallProcessSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtain.ZcCurtainInstallProcessDO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 智仓安装工艺")
@RestController
@RequestMapping("/zc/curtain-install-process")
@Validated
public class ZcCurtainInstallProcessController {

    @Resource
    private ZcCurtainInstallProcessService curtainInstallProcessService;

    @PostMapping("/create")
    @Operation(summary = "创建安装工艺")
    @PreAuthorize("@ss.hasPermission('zc:curtain-install-process:create')")
    public CommonResult<Long> create(@Valid @RequestBody ZcCurtainInstallProcessSaveReqVO reqVO) {
        return success(curtainInstallProcessService.create(reqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新安装工艺")
    @PreAuthorize("@ss.hasPermission('zc:curtain-install-process:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody ZcCurtainInstallProcessSaveReqVO reqVO) {
        curtainInstallProcessService.update(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除安装工艺")
    @PreAuthorize("@ss.hasPermission('zc:curtain-install-process:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        curtainInstallProcessService.delete(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得安装工艺")
    @PreAuthorize("@ss.hasPermission('zc:curtain-install-process:query')")
    public CommonResult<ZcCurtainInstallProcessSaveReqVO> get(@RequestParam("id") Long id) {
        return success(BeanUtils.toBean(curtainInstallProcessService.get(id), ZcCurtainInstallProcessSaveReqVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "安装工艺分页")
    @PreAuthorize("@ss.hasPermission('zc:curtain-install-process:query')")
    public CommonResult<PageResult<ZcCurtainInstallProcessSaveReqVO>> page(
            @Valid ZcCurtainInstallProcessPageReqVO pageReqVO) {
        PageResult<ZcCurtainInstallProcessDO> pageResult = curtainInstallProcessService.getPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ZcCurtainInstallProcessSaveReqVO.class));
    }

}
