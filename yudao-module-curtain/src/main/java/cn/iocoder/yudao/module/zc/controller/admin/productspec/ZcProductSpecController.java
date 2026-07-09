package cn.iocoder.yudao.module.zc.controller.admin.productspec;

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

import cn.iocoder.yudao.module.zc.controller.admin.productspec.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.productspec.ZcProductSpecDO;
import cn.iocoder.yudao.module.zc.service.productspec.ZcProductSpecService;

@Tag(name = "管理后台 - 产品规格")
@RestController
@RequestMapping("/zc/product-spec")
@Validated
public class ZcProductSpecController {

    @Resource
    private ZcProductSpecService productSpecService;

    @PostMapping("/create")
    @Operation(summary = "创建产品规格")
    @PreAuthorize("@ss.hasPermission('zc:product-spec:create')")
    public CommonResult<Long> createProductSpec(@Valid @RequestBody ZcProductSpecSaveReqVO createReqVO) {
        return success(productSpecService.createProductSpec(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新产品规格")
    @PreAuthorize("@ss.hasPermission('zc:product-spec:update')")
    public CommonResult<Boolean> updateProductSpec(@Valid @RequestBody ZcProductSpecSaveReqVO updateReqVO) {
        productSpecService.updateProductSpec(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除产品规格")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('zc:product-spec:delete')")
    public CommonResult<Boolean> deleteProductSpec(@RequestParam("id") Long id) {
        productSpecService.deleteProductSpec(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除产品规格")
                @PreAuthorize("@ss.hasPermission('zc:product-spec:delete')")
    public CommonResult<Boolean> deleteProductSpecList(@RequestParam("ids") List<Long> ids) {
        productSpecService.deleteProductSpecListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得产品规格")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('zc:product-spec:query')")
    public CommonResult<ZcProductSpecRespVO> getProductSpec(@RequestParam("id") Long id) {
        ZcProductSpecDO productSpec = productSpecService.getProductSpec(id);
        return success(BeanUtils.toBean(productSpec, ZcProductSpecRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得产品规格分页")
    @PreAuthorize("@ss.hasPermission('zc:product-spec:query')")
    public CommonResult<PageResult<ZcProductSpecRespVO>> getProductSpecPage(@Valid ZcProductSpecPageReqVO pageReqVO) {
        PageResult<ZcProductSpecDO> pageResult = productSpecService.getProductSpecPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ZcProductSpecRespVO.class));
    }

    @GetMapping("/simple-list")
    @Operation(summary = "获得产品规格精简列表", description = "主要用于前端的下拉选项")
    public CommonResult<List<ZcProductSpecSimpleRespVO>> getProductSpecSimpleList() {
        List<ZcProductSpecDO> list = productSpecService.getProductSpecList(new ZcProductSpecListReqVO());
        return success(convertList(list, item -> new ZcProductSpecSimpleRespVO()
                .setId(item.getId())
                .setValue(item.getValue())));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出产品规格 Excel")
    @PreAuthorize("@ss.hasPermission('zc:product-spec:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportProductSpecExcel(@Valid ZcProductSpecPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ZcProductSpecDO> list = productSpecService.getProductSpecPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "产品规格.xls", "数据", ZcProductSpecRespVO.class,
                        BeanUtils.toBean(list, ZcProductSpecRespVO.class));
    }

}