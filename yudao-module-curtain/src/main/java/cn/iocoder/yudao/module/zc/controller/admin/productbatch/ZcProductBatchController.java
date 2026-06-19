package cn.iocoder.yudao.module.zc.controller.admin.productbatch;

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
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.zc.controller.admin.productbatch.vo.*;
import cn.iocoder.yudao.module.zc.service.productbatch.ZcProductBatchService;

@Tag(name = "管理后台 - 产品批次")
@RestController
@RequestMapping("/zc/product-batch")
@Validated
public class ZcProductBatchController {

    @Resource
    private ZcProductBatchService productBatchService;

    @PostMapping("/create-batch")
    @Operation(summary = "批量创建产品批次")
    @PreAuthorize("@ss.hasPermission('zc:product-batch:create')")
    public CommonResult<List<ZcProductBatchRespVO>> createProductBatchList(@Valid @RequestBody List<ZcProductBatchSaveReqVO> createReqVOs) {
        return success(productBatchService.createProductBatchList(createReqVOs));
    }

    @PutMapping("/update")
    @Operation(summary = "更新产品批次")
    @PreAuthorize("@ss.hasPermission('zc:product-batch:update')")
    public CommonResult<Boolean> updateProductBatch(@Valid @RequestBody ZcProductBatchSaveReqVO updateReqVO) {
        productBatchService.updateProductBatch(updateReqVO);
        return success(true);
    }

    @PutMapping("/update-status")
    @Operation(summary = "更新产品批次状态")
    @PreAuthorize("@ss.hasPermission('zc:product-batch:update')")
    public CommonResult<Boolean> updateProductBatchStatus(@Valid @RequestBody ZcProductBatchUpdateStatusReqVO updateReqVO) {
        productBatchService.updateProductBatchStatus(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除产品批次")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('zc:product-batch:delete')")
    public CommonResult<Boolean> deleteProductBatch(@RequestParam("id") Long id) {
        productBatchService.deleteProductBatch(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除产品批次")
                @PreAuthorize("@ss.hasPermission('zc:product-batch:delete')")
    public CommonResult<Boolean> deleteProductBatchList(@RequestParam("ids") List<Long> ids) {
        productBatchService.deleteProductBatchListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得产品批次")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('zc:product-batch:query')")
    public CommonResult<ZcProductBatchRespVO> getProductBatch(@RequestParam("id") Long id) {
        return success(productBatchService.getProductBatch(id));
    }

    @GetMapping("/page")
    @Operation(summary = "获得产品批次分页")
    @PreAuthorize("@ss.hasPermission('zc:product-batch:query')")
    public CommonResult<PageResult<ZcProductBatchRespVO>> getProductBatchPage(@Valid ZcProductBatchPageReqVO pageReqVO) {
        return success(productBatchService.getProductBatchPage(pageReqVO));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出产品批次 Excel")
    @PreAuthorize("@ss.hasPermission('zc:product-batch:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportProductBatchExcel(@Valid ZcProductBatchPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ZcProductBatchRespVO> list = productBatchService.getProductBatchPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "产品批次.xls", "数据", ZcProductBatchRespVO.class, list);
    }

}