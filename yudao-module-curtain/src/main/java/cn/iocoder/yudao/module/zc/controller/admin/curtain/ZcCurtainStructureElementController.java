package cn.iocoder.yudao.module.zc.controller.admin.curtain;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.zc.service.curtain.ZcCurtainStructureElementService;
import cn.iocoder.yudao.module.zc.controller.admin.vo.curtain.ZcCurtainStructureElementPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.curtain.ZcCurtainStructureElementSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtain.ZcCurtainStructureElementDO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 智仓窗帘结构部件")
@RestController
@RequestMapping("/zc/curtain-structure-element")
@Validated
public class ZcCurtainStructureElementController {

    @Resource
    private ZcCurtainStructureElementService curtainStructureElementService;

    @PostMapping("/create")
    @Operation(summary = "创建结构部件")
    @PreAuthorize("@ss.hasPermission('zc:curtain-structure-element:create')")
    public CommonResult<Long> create(@Valid @RequestBody ZcCurtainStructureElementSaveReqVO reqVO) {
        return success(curtainStructureElementService.create(reqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新结构部件")
    @PreAuthorize("@ss.hasPermission('zc:curtain-structure-element:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody ZcCurtainStructureElementSaveReqVO reqVO) {
        curtainStructureElementService.update(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除结构部件")
    @PreAuthorize("@ss.hasPermission('zc:curtain-structure-element:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        curtainStructureElementService.delete(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得结构部件")
    @PreAuthorize("@ss.hasPermission('zc:curtain-structure-element:query')")
    public CommonResult<ZcCurtainStructureElementSaveReqVO> get(@RequestParam("id") Long id) {
        return success(BeanUtils.toBean(curtainStructureElementService.get(id), ZcCurtainStructureElementSaveReqVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "结构部件分页")
    @PreAuthorize("@ss.hasPermission('zc:curtain-structure-element:query')")
    public CommonResult<PageResult<ZcCurtainStructureElementSaveReqVO>> page(
            @Valid ZcCurtainStructureElementPageReqVO pageReqVO) {
        PageResult<ZcCurtainStructureElementDO> pageResult = curtainStructureElementService.getPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ZcCurtainStructureElementSaveReqVO.class));
    }

}
