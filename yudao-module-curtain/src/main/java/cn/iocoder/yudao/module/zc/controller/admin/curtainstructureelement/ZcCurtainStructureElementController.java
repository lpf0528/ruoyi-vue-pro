package cn.iocoder.yudao.module.zc.controller.admin.curtainstructureelement;

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

import cn.iocoder.yudao.module.zc.controller.admin.curtainstructureelement.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtainstructureelement.ZcCurtainStructureElementDO;
import cn.iocoder.yudao.module.zc.service.curtainstructureelement.ZcCurtainStructureElementService;

@Tag(name = "管理后台 - 窗帘结构组件")
@RestController
@RequestMapping("/zc/curtain-structure-element")
@Validated
public class ZcCurtainStructureElementController {

    @Resource
    private ZcCurtainStructureElementService curtainStructureElementService;

    @PostMapping("/create")
    @Operation(summary = "创建窗帘结构组件")
    @PreAuthorize("@ss.hasPermission('zc:curtain-structure-element:create')")
    public CommonResult<Long> createCurtainStructureElement(@Valid @RequestBody ZcCurtainStructureElementSaveReqVO createReqVO) {
        return success(curtainStructureElementService.createCurtainStructureElement(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新窗帘结构组件")
    @PreAuthorize("@ss.hasPermission('zc:curtain-structure-element:update')")
    public CommonResult<Boolean> updateCurtainStructureElement(@Valid @RequestBody ZcCurtainStructureElementSaveReqVO updateReqVO) {
        curtainStructureElementService.updateCurtainStructureElement(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除窗帘结构组件")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('zc:curtain-structure-element:delete')")
    public CommonResult<Boolean> deleteCurtainStructureElement(@RequestParam("id") Long id) {
        curtainStructureElementService.deleteCurtainStructureElement(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除窗帘结构组件")
                @PreAuthorize("@ss.hasPermission('zc:curtain-structure-element:delete')")
    public CommonResult<Boolean> deleteCurtainStructureElementList(@RequestParam("ids") List<Long> ids) {
        curtainStructureElementService.deleteCurtainStructureElementListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得窗帘结构组件")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('zc:curtain-structure-element:query')")
    public CommonResult<ZcCurtainStructureElementRespVO> getCurtainStructureElement(@RequestParam("id") Long id) {
        ZcCurtainStructureElementDO curtainStructureElement = curtainStructureElementService.getCurtainStructureElement(id);
        return success(BeanUtils.toBean(curtainStructureElement, ZcCurtainStructureElementRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得窗帘结构组件分页")
    @PreAuthorize("@ss.hasPermission('zc:curtain-structure-element:query')")
    public CommonResult<PageResult<ZcCurtainStructureElementRespVO>> getCurtainStructureElementPage(@Valid ZcCurtainStructureElementPageReqVO pageReqVO) {
        return success(curtainStructureElementService.getCurtainStructureElementPage(pageReqVO));
    }

    @GetMapping("/simple-list")
    @Operation(summary = "获得窗帘结构组件精简列表", description = "主要用于前端的下拉选项")
    public CommonResult<List<ZcCurtainStructureElementSimpleRespVO>> getCurtainStructureElementSimpleList() {
        List<ZcCurtainStructureElementDO> list = curtainStructureElementService.getCurtainStructureElementList(
                new ZcCurtainStructureElementListReqVO());
        return success(convertList(list, item -> new ZcCurtainStructureElementSimpleRespVO()
                .setId(item.getId())
                .setName(item.getName())
                .setIsPrint(item.getIsPrint())
        ));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出窗帘结构组件 Excel")
    @PreAuthorize("@ss.hasPermission('zc:curtain-structure-element:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportCurtainStructureElementExcel(@Valid ZcCurtainStructureElementPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ZcCurtainStructureElementRespVO> list = curtainStructureElementService.getCurtainStructureElementPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "窗帘结构组件.xls", "数据", ZcCurtainStructureElementRespVO.class, list);
    }

}