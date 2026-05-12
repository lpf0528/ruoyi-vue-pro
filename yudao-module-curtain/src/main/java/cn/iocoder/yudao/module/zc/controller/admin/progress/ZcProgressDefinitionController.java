package cn.iocoder.yudao.module.zc.controller.admin.progress;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.zc.controller.admin.vo.progress.ZcProgressDefinitionPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.progress.ZcProgressDefinitionSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.progress.ZcProgressDefinitionDO;
import cn.iocoder.yudao.module.zc.service.progress.ZcProgressDefinitionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 智仓进度定义")
@RestController
@RequestMapping("/zc/progress-definition")
@Validated
public class ZcProgressDefinitionController {

    @Resource
    private ZcProgressDefinitionService progressDefinitionService;

    @PostMapping("/create")
    @Operation(summary = "创建进度定义")
    @PreAuthorize("@ss.hasPermission('zc:progress-definition:create')")
    public CommonResult<Long> create(@Valid @RequestBody ZcProgressDefinitionSaveReqVO reqVO) {
        return success(progressDefinitionService.create(reqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新进度定义")
    @PreAuthorize("@ss.hasPermission('zc:progress-definition:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody ZcProgressDefinitionSaveReqVO reqVO) {
        progressDefinitionService.update(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除进度定义")
    @PreAuthorize("@ss.hasPermission('zc:progress-definition:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        progressDefinitionService.delete(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得进度定义")
    @PreAuthorize("@ss.hasPermission('zc:progress-definition:query')")
    public CommonResult<ZcProgressDefinitionSaveReqVO> get(@RequestParam("id") Long id) {
        return success(BeanUtils.toBean(progressDefinitionService.get(id), ZcProgressDefinitionSaveReqVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "进度定义分页")
    @PreAuthorize("@ss.hasPermission('zc:progress-definition:query')")
    public CommonResult<PageResult<ZcProgressDefinitionSaveReqVO>> page(@Valid ZcProgressDefinitionPageReqVO pageReqVO) {
        PageResult<ZcProgressDefinitionDO> pageResult = progressDefinitionService.getPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ZcProgressDefinitionSaveReqVO.class));
    }

}
