package cn.iocoder.yudao.module.zc.controller.admin.curtaininstallprocess;

import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.constraints.*;
import jakarta.validation.*;
import jakarta.servlet.http.*;
import java.util.*;
import java.io.IOException;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.zc.controller.admin.curtaininstallprocess.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtaininstallprocess.ZcCurtainInstallProcessDO;
import cn.iocoder.yudao.module.zc.service.curtaininstallprocess.ZcCurtainInstallProcessService;

@Tag(name = "管理后台 - 安装工艺")
@RestController
@RequestMapping("/zc/curtain-install-process")
@Validated
public class ZcCurtainInstallProcessController {

    @Resource
    private ZcCurtainInstallProcessService curtainInstallProcessService;

    @PostMapping("/create")
    @Operation(summary = "创建安装工艺")
    @PreAuthorize("@ss.hasPermission('zc:curtain-install-process:create')")
    public CommonResult<Long> createCurtainInstallProcess(@Valid @RequestBody ZcCurtainInstallProcessSaveReqVO createReqVO) {
        return success(curtainInstallProcessService.createCurtainInstallProcess(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新安装工艺")
    @PreAuthorize("@ss.hasPermission('zc:curtain-install-process:update')")
    public CommonResult<Boolean> updateCurtainInstallProcess(@Valid @RequestBody ZcCurtainInstallProcessSaveReqVO updateReqVO) {
        curtainInstallProcessService.updateCurtainInstallProcess(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除安装工艺")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('zc:curtain-install-process:delete')")
    public CommonResult<Boolean> deleteCurtainInstallProcess(@RequestParam("id") Long id) {
        curtainInstallProcessService.deleteCurtainInstallProcess(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除安装工艺")
                @PreAuthorize("@ss.hasPermission('zc:curtain-install-process:delete')")
    public CommonResult<Boolean> deleteCurtainInstallProcessList(@RequestParam("ids") List<Long> ids) {
        curtainInstallProcessService.deleteCurtainInstallProcessListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得安装工艺")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('zc:curtain-install-process:query')")
    public CommonResult<ZcCurtainInstallProcessRespVO> getCurtainInstallProcess(@RequestParam("id") Long id) {
        ZcCurtainInstallProcessDO curtainInstallProcess = curtainInstallProcessService.getCurtainInstallProcess(id);
        return success(BeanUtils.toBean(curtainInstallProcess, ZcCurtainInstallProcessRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得安装工艺分页")
    @PreAuthorize("@ss.hasPermission('zc:curtain-install-process:query')")
    public CommonResult<PageResult<ZcCurtainInstallProcessRespVO>> getCurtainInstallProcessPage(@Valid ZcCurtainInstallProcessPageReqVO pageReqVO) {
        PageResult<ZcCurtainInstallProcessDO> pageResult = curtainInstallProcessService.getCurtainInstallProcessPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ZcCurtainInstallProcessRespVO.class));
    }

    @GetMapping("/simple-list")
    @Operation(summary = "获得安装工艺精简列表", description = "主要用于前端的下拉选项")
    public CommonResult<List<ZcCurtainInstallProcessSimpleRespVO>> getCurtainInstallProcessSimpleList() {
        List<ZcCurtainInstallProcessDO> list = curtainInstallProcessService.getCurtainInstallProcessList(
                new ZcCurtainInstallProcessListReqVO());
        return success(convertList(list, item -> new ZcCurtainInstallProcessSimpleRespVO()
                .setId(item.getId())
                .setName(item.getName())
                .setNodeIds(item.getNodeIds())));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出安装工艺 Excel")
    @PreAuthorize("@ss.hasPermission('zc:curtain-install-process:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportCurtainInstallProcessExcel(@Valid ZcCurtainInstallProcessPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ZcCurtainInstallProcessDO> list = curtainInstallProcessService.getCurtainInstallProcessPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "安装工艺.xls", "数据", ZcCurtainInstallProcessRespVO.class,
                        BeanUtils.toBean(list, ZcCurtainInstallProcessRespVO.class));
    }

}