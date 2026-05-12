package cn.iocoder.yudao.module.zc.controller.admin.curtain;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.zc.service.curtain.ZcCurtainStyleService;
import cn.iocoder.yudao.module.zc.controller.admin.vo.curtain.ZcCurtainStylePageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.curtain.ZcCurtainStyleSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtain.ZcCurtainStyleDO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 智仓窗帘款式")
@RestController
@RequestMapping("/zc/curtain-style")
@Validated
public class ZcCurtainStyleController {

    @Resource
    private ZcCurtainStyleService curtainStyleService;

    @PostMapping("/create")
    @Operation(summary = "创建窗帘款式")
    @PreAuthorize("@ss.hasPermission('zc:curtain-style:create')")
    public CommonResult<Long> create(@Valid @RequestBody ZcCurtainStyleSaveReqVO reqVO) {
        return success(curtainStyleService.create(reqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新窗帘款式")
    @PreAuthorize("@ss.hasPermission('zc:curtain-style:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody ZcCurtainStyleSaveReqVO reqVO) {
        curtainStyleService.update(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除窗帘款式")
    @PreAuthorize("@ss.hasPermission('zc:curtain-style:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        curtainStyleService.delete(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得窗帘款式")
    @PreAuthorize("@ss.hasPermission('zc:curtain-style:query')")
    public CommonResult<ZcCurtainStyleSaveReqVO> get(@RequestParam("id") Long id) {
        return success(BeanUtils.toBean(curtainStyleService.get(id), ZcCurtainStyleSaveReqVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "窗帘款式分页")
    @PreAuthorize("@ss.hasPermission('zc:curtain-style:query')")
    public CommonResult<PageResult<ZcCurtainStyleSaveReqVO>> page(@Valid ZcCurtainStylePageReqVO pageReqVO) {
        PageResult<ZcCurtainStyleDO> pageResult = curtainStyleService.getPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ZcCurtainStyleSaveReqVO.class));
    }

}
