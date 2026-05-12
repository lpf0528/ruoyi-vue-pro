package cn.iocoder.yudao.module.zc.controller.admin.curtain;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.zc.service.curtain.ZcCurtainStructureService;
import cn.iocoder.yudao.module.zc.controller.admin.vo.curtain.ZcCurtainStructurePageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.curtain.ZcCurtainStructureSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtain.ZcCurtainStructureDO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 智仓窗帘结构")
@RestController
@RequestMapping("/zc/curtain-structure")
@Validated
public class ZcCurtainStructureController {

    @Resource
    private ZcCurtainStructureService curtainStructureService;

    @PostMapping("/create")
    @Operation(summary = "创建窗帘结构")
    @PreAuthorize("@ss.hasPermission('zc:curtain-structure:create')")
    public CommonResult<Long> create(@Valid @RequestBody ZcCurtainStructureSaveReqVO reqVO) {
        return success(curtainStructureService.create(reqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新窗帘结构")
    @PreAuthorize("@ss.hasPermission('zc:curtain-structure:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody ZcCurtainStructureSaveReqVO reqVO) {
        curtainStructureService.update(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除窗帘结构")
    @PreAuthorize("@ss.hasPermission('zc:curtain-structure:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        curtainStructureService.delete(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得窗帘结构")
    @PreAuthorize("@ss.hasPermission('zc:curtain-structure:query')")
    public CommonResult<ZcCurtainStructureSaveReqVO> get(@RequestParam("id") Long id) {
        return success(BeanUtils.toBean(curtainStructureService.get(id), ZcCurtainStructureSaveReqVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "窗帘结构分页")
    @PreAuthorize("@ss.hasPermission('zc:curtain-structure:query')")
    public CommonResult<PageResult<ZcCurtainStructureSaveReqVO>> page(@Valid ZcCurtainStructurePageReqVO pageReqVO) {
        PageResult<ZcCurtainStructureDO> pageResult = curtainStructureService.getPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ZcCurtainStructureSaveReqVO.class));
    }

}
