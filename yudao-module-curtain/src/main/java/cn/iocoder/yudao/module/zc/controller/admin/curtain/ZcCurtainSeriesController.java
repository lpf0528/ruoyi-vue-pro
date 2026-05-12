package cn.iocoder.yudao.module.zc.controller.admin.curtain;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.zc.service.curtain.ZcCurtainSeriesService;
import cn.iocoder.yudao.module.zc.controller.admin.vo.curtain.ZcCurtainSeriesPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.curtain.ZcCurtainSeriesSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtain.ZcCurtainSeriesDO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 智仓窗帘系列")
@RestController
@RequestMapping("/zc/curtain-series")
@Validated
public class ZcCurtainSeriesController {

    @Resource
    private ZcCurtainSeriesService curtainSeriesService;

    @PostMapping("/create")
    @Operation(summary = "创建窗帘系列")
    @PreAuthorize("@ss.hasPermission('zc:curtain-series:create')")
    public CommonResult<Long> create(@Valid @RequestBody ZcCurtainSeriesSaveReqVO reqVO) {
        return success(curtainSeriesService.create(reqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新窗帘系列")
    @PreAuthorize("@ss.hasPermission('zc:curtain-series:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody ZcCurtainSeriesSaveReqVO reqVO) {
        curtainSeriesService.update(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除窗帘系列")
    @PreAuthorize("@ss.hasPermission('zc:curtain-series:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        curtainSeriesService.delete(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得窗帘系列")
    @PreAuthorize("@ss.hasPermission('zc:curtain-series:query')")
    public CommonResult<ZcCurtainSeriesSaveReqVO> get(@RequestParam("id") Long id) {
        return success(BeanUtils.toBean(curtainSeriesService.get(id), ZcCurtainSeriesSaveReqVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "窗帘系列分页")
    @PreAuthorize("@ss.hasPermission('zc:curtain-series:query')")
    public CommonResult<PageResult<ZcCurtainSeriesSaveReqVO>> page(@Valid ZcCurtainSeriesPageReqVO pageReqVO) {
        PageResult<ZcCurtainSeriesDO> pageResult = curtainSeriesService.getPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ZcCurtainSeriesSaveReqVO.class));
    }

}
