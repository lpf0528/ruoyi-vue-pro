package cn.iocoder.yudao.module.zc.controller.admin.curtainstructure;

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

import cn.iocoder.yudao.module.zc.controller.admin.curtainstructure.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtainstructure.CurtainStructureDO;
import cn.iocoder.yudao.module.zc.service.curtainstructure.CurtainStructureService;

@Tag(name = "管理后台 - 窗帘结构部位")
@RestController
@RequestMapping("/zc/curtain-structure")
@Validated
public class CurtainStructureController {

    @Resource
    private CurtainStructureService curtainStructureService;

    @PostMapping("/create")
    @Operation(summary = "创建窗帘结构部位")
    @PreAuthorize("@ss.hasPermission('zc:curtain-structure:create')")
    public CommonResult<Long> createCurtainStructure(@Valid @RequestBody CurtainStructureSaveReqVO createReqVO) {
        return success(curtainStructureService.createCurtainStructure(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新窗帘结构部位")
    @PreAuthorize("@ss.hasPermission('zc:curtain-structure:update')")
    public CommonResult<Boolean> updateCurtainStructure(@Valid @RequestBody CurtainStructureSaveReqVO updateReqVO) {
        curtainStructureService.updateCurtainStructure(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除窗帘结构部位")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('zc:curtain-structure:delete')")
    public CommonResult<Boolean> deleteCurtainStructure(@RequestParam("id") Long id) {
        curtainStructureService.deleteCurtainStructure(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除窗帘结构部位")
                @PreAuthorize("@ss.hasPermission('zc:curtain-structure:delete')")
    public CommonResult<Boolean> deleteCurtainStructureList(@RequestParam("ids") List<Long> ids) {
        curtainStructureService.deleteCurtainStructureListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得窗帘结构部位")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('zc:curtain-structure:query')")
    public CommonResult<CurtainStructureRespVO> getCurtainStructure(@RequestParam("id") Long id) {
        CurtainStructureDO curtainStructure = curtainStructureService.getCurtainStructure(id);
        return success(BeanUtils.toBean(curtainStructure, CurtainStructureRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得窗帘结构部位分页")
    @PreAuthorize("@ss.hasPermission('zc:curtain-structure:query')")
    public CommonResult<PageResult<CurtainStructureRespVO>> getCurtainStructurePage(@Valid CurtainStructurePageReqVO pageReqVO) {
        PageResult<CurtainStructureDO> pageResult = curtainStructureService.getCurtainStructurePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, CurtainStructureRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出窗帘结构部位 Excel")
    @PreAuthorize("@ss.hasPermission('zc:curtain-structure:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportCurtainStructureExcel(@Valid CurtainStructurePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<CurtainStructureDO> list = curtainStructureService.getCurtainStructurePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "窗帘结构部位.xls", "数据", CurtainStructureRespVO.class,
                        BeanUtils.toBean(list, CurtainStructureRespVO.class));
    }

}