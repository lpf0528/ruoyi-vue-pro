package cn.iocoder.yudao.module.zc.controller.admin.curtainpleatratio;

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

import cn.iocoder.yudao.module.zc.controller.admin.curtainpleatratio.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtainpleatratio.ZcCurtainPleatRatioDO;
import cn.iocoder.yudao.module.zc.service.curtainpleatratio.ZcCurtainPleatRatioService;

@Tag(name = "管理后台 - 褶倍")
@RestController
@RequestMapping("/zc/curtain-pleat-ratio")
@Validated
public class ZcCurtainPleatRatioController {

    @Resource
    private ZcCurtainPleatRatioService curtainPleatRatioService;

    @PostMapping("/create")
    @Operation(summary = "创建褶倍")
    @PreAuthorize("@ss.hasPermission('zc:curtain-pleat-ratio:create')")
    public CommonResult<Long> createCurtainPleatRatio(@Valid @RequestBody ZcCurtainPleatRatioSaveReqVO createReqVO) {
        return success(curtainPleatRatioService.createCurtainPleatRatio(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新褶倍")
    @PreAuthorize("@ss.hasPermission('zc:curtain-pleat-ratio:update')")
    public CommonResult<Boolean> updateCurtainPleatRatio(@Valid @RequestBody ZcCurtainPleatRatioSaveReqVO updateReqVO) {
        curtainPleatRatioService.updateCurtainPleatRatio(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除褶倍")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('zc:curtain-pleat-ratio:delete')")
    public CommonResult<Boolean> deleteCurtainPleatRatio(@RequestParam("id") Long id) {
        curtainPleatRatioService.deleteCurtainPleatRatio(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除褶倍")
                @PreAuthorize("@ss.hasPermission('zc:curtain-pleat-ratio:delete')")
    public CommonResult<Boolean> deleteCurtainPleatRatioList(@RequestParam("ids") List<Long> ids) {
        curtainPleatRatioService.deleteCurtainPleatRatioListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得褶倍")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('zc:curtain-pleat-ratio:query')")
    public CommonResult<ZcCurtainPleatRatioRespVO> getCurtainPleatRatio(@RequestParam("id") Long id) {
        ZcCurtainPleatRatioDO curtainPleatRatio = curtainPleatRatioService.getCurtainPleatRatio(id);
        return success(BeanUtils.toBean(curtainPleatRatio, ZcCurtainPleatRatioRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得褶倍分页")
    @PreAuthorize("@ss.hasPermission('zc:curtain-pleat-ratio:query')")
    public CommonResult<PageResult<ZcCurtainPleatRatioRespVO>> getCurtainPleatRatioPage(@Valid ZcCurtainPleatRatioPageReqVO pageReqVO) {
        PageResult<ZcCurtainPleatRatioDO> pageResult = curtainPleatRatioService.getCurtainPleatRatioPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ZcCurtainPleatRatioRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出褶倍 Excel")
    @PreAuthorize("@ss.hasPermission('zc:curtain-pleat-ratio:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportCurtainPleatRatioExcel(@Valid ZcCurtainPleatRatioPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ZcCurtainPleatRatioDO> list = curtainPleatRatioService.getCurtainPleatRatioPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "褶倍.xls", "数据", ZcCurtainPleatRatioRespVO.class,
                        BeanUtils.toBean(list, ZcCurtainPleatRatioRespVO.class));
    }

}