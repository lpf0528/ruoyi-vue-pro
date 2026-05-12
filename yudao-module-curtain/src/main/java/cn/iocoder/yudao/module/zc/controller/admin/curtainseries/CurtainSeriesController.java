package cn.iocoder.yudao.module.zc.controller.admin.curtainseries;

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

import cn.iocoder.yudao.module.zc.controller.admin.curtainseries.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtainseries.CurtainSeriesDO;
import cn.iocoder.yudao.module.zc.service.curtainseries.CurtainSeriesService;

@Tag(name = "管理后台 - 窗帘系列")
@RestController
@RequestMapping("/zc/curtain-series")
@Validated
public class CurtainSeriesController {

    @Resource
    private CurtainSeriesService curtainSeriesService;

    @PostMapping("/create")
    @Operation(summary = "创建窗帘系列")
    @PreAuthorize("@ss.hasPermission('zc:curtain-series:create')")
    public CommonResult<Long> createCurtainSeries(@Valid @RequestBody CurtainSeriesSaveReqVO createReqVO) {
        return success(curtainSeriesService.createCurtainSeries(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新窗帘系列")
    @PreAuthorize("@ss.hasPermission('zc:curtain-series:update')")
    public CommonResult<Boolean> updateCurtainSeries(@Valid @RequestBody CurtainSeriesSaveReqVO updateReqVO) {
        curtainSeriesService.updateCurtainSeries(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除窗帘系列")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('zc:curtain-series:delete')")
    public CommonResult<Boolean> deleteCurtainSeries(@RequestParam("id") Long id) {
        curtainSeriesService.deleteCurtainSeries(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除窗帘系列")
                @PreAuthorize("@ss.hasPermission('zc:curtain-series:delete')")
    public CommonResult<Boolean> deleteCurtainSeriesList(@RequestParam("ids") List<Long> ids) {
        curtainSeriesService.deleteCurtainSeriesListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得窗帘系列")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('zc:curtain-series:query')")
    public CommonResult<CurtainSeriesRespVO> getCurtainSeries(@RequestParam("id") Long id) {
        CurtainSeriesDO curtainSeries = curtainSeriesService.getCurtainSeries(id);
        return success(BeanUtils.toBean(curtainSeries, CurtainSeriesRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得窗帘系列分页")
    @PreAuthorize("@ss.hasPermission('zc:curtain-series:query')")
    public CommonResult<PageResult<CurtainSeriesRespVO>> getCurtainSeriesPage(@Valid CurtainSeriesPageReqVO pageReqVO) {
        PageResult<CurtainSeriesDO> pageResult = curtainSeriesService.getCurtainSeriesPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, CurtainSeriesRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出窗帘系列 Excel")
    @PreAuthorize("@ss.hasPermission('zc:curtain-series:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportCurtainSeriesExcel(@Valid CurtainSeriesPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<CurtainSeriesDO> list = curtainSeriesService.getCurtainSeriesPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "窗帘系列.xls", "数据", CurtainSeriesRespVO.class,
                        BeanUtils.toBean(list, CurtainSeriesRespVO.class));
    }

}