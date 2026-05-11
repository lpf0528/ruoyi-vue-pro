package cn.iocoder.yudao.module.zc.controller.admin.curtain;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.zc.service.curtain.ZcCurtainPleatRatioService;
import cn.iocoder.yudao.module.zc.controller.admin.vo.curtain.ZcCurtainPleatRatioPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.curtain.ZcCurtainPleatRatioSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtain.ZcCurtainPleatRatioDO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 智仓褶皱倍数")
@RestController
@RequestMapping("/zc/curtain-pleat-ratio")
@Validated
public class ZcCurtainPleatRatioController {

    @Resource
    private ZcCurtainPleatRatioService curtainPleatRatioService;

    @PostMapping("/create")
    @Operation(summary = "创建褶皱倍数")
    @PreAuthorize("@ss.hasPermission('zc:curtain-pleat-ratio:create')")
    public CommonResult<Long> create(@Valid @RequestBody ZcCurtainPleatRatioSaveReqVO reqVO) {
        return success(curtainPleatRatioService.create(reqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新褶皱倍数")
    @PreAuthorize("@ss.hasPermission('zc:curtain-pleat-ratio:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody ZcCurtainPleatRatioSaveReqVO reqVO) {
        curtainPleatRatioService.update(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除褶皱倍数")
    @PreAuthorize("@ss.hasPermission('zc:curtain-pleat-ratio:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        curtainPleatRatioService.delete(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得褶皱倍数")
    @PreAuthorize("@ss.hasPermission('zc:curtain-pleat-ratio:query')")
    public CommonResult<ZcCurtainPleatRatioSaveReqVO> get(@RequestParam("id") Long id) {
        return success(BeanUtils.toBean(curtainPleatRatioService.get(id), ZcCurtainPleatRatioSaveReqVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "褶皱倍数分页")
    @PreAuthorize("@ss.hasPermission('zc:curtain-pleat-ratio:query')")
    public CommonResult<PageResult<ZcCurtainPleatRatioSaveReqVO>> page(@Valid ZcCurtainPleatRatioPageReqVO pageReqVO) {
        PageResult<ZcCurtainPleatRatioDO> pageResult = curtainPleatRatioService.getPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ZcCurtainPleatRatioSaveReqVO.class));
    }

}
