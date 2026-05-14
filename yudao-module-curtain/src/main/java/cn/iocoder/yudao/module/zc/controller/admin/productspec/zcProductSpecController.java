package cn.iocoder.yudao.module.zc.controller.admin.productspec;

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

import cn.iocoder.yudao.module.zc.controller.admin.productspec.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.productspec.zcProductSpecDO;
import cn.iocoder.yudao.module.zc.service.productspec.zcProductSpecService;

@Tag(name = "管理后台 - 产品规格")
@RestController
@RequestMapping("/zc/zc-product-spec")
@Validated
public class zcProductSpecController {

    @Resource
    private zcProductSpecService zcProductSpecService;

    @PostMapping("/create")
    @Operation(summary = "创建产品规格")
    @PreAuthorize("@ss.hasPermission('zc:zc-product-spec:create')")
    public CommonResult<Long> createzcProductSpec(@Valid @RequestBody zcProductSpecSaveReqVO createReqVO) {
        return success(zcProductSpecService.createzcProductSpec(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新产品规格")
    @PreAuthorize("@ss.hasPermission('zc:zc-product-spec:update')")
    public CommonResult<Boolean> updatezcProductSpec(@Valid @RequestBody zcProductSpecSaveReqVO updateReqVO) {
        zcProductSpecService.updatezcProductSpec(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除产品规格")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('zc:zc-product-spec:delete')")
    public CommonResult<Boolean> deletezcProductSpec(@RequestParam("id") Long id) {
        zcProductSpecService.deletezcProductSpec(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除产品规格")
                @PreAuthorize("@ss.hasPermission('zc:zc-product-spec:delete')")
    public CommonResult<Boolean> deletezcProductSpecList(@RequestParam("ids") List<Long> ids) {
        zcProductSpecService.deletezcProductSpecListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得产品规格")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('zc:zc-product-spec:query')")
    public CommonResult<zcProductSpecRespVO> getzcProductSpec(@RequestParam("id") Long id) {
        zcProductSpecDO zcProductSpec = zcProductSpecService.getzcProductSpec(id);
        return success(BeanUtils.toBean(zcProductSpec, zcProductSpecRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得产品规格分页")
    @PreAuthorize("@ss.hasPermission('zc:zc-product-spec:query')")
    public CommonResult<PageResult<zcProductSpecRespVO>> getzcProductSpecPage(@Valid zcProductSpecPageReqVO pageReqVO) {
        PageResult<zcProductSpecDO> pageResult = zcProductSpecService.getzcProductSpecPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, zcProductSpecRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出产品规格 Excel")
    @PreAuthorize("@ss.hasPermission('zc:zc-product-spec:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportzcProductSpecExcel(@Valid zcProductSpecPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<zcProductSpecDO> list = zcProductSpecService.getzcProductSpecPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "产品规格.xls", "数据", zcProductSpecRespVO.class,
                        BeanUtils.toBean(list, zcProductSpecRespVO.class));
    }

}