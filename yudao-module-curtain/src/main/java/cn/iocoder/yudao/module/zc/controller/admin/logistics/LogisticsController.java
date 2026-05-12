package cn.iocoder.yudao.module.zc.controller.admin.logistics;

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

import cn.iocoder.yudao.module.zc.controller.admin.logistics.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.logistics.LogisticsDO;
import cn.iocoder.yudao.module.zc.service.logistics.LogisticsService;

@Tag(name = "管理后台 - 物流公司")
@RestController
@RequestMapping("/zc/logistics")
@Validated
public class LogisticsController {

    @Resource
    private LogisticsService logisticsService;

    @PostMapping("/create")
    @Operation(summary = "创建物流公司")
    @PreAuthorize("@ss.hasPermission('zc:logistics:create')")
    public CommonResult<Long> createLogistics(@Valid @RequestBody LogisticsSaveReqVO createReqVO) {
        return success(logisticsService.createLogistics(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新物流公司")
    @PreAuthorize("@ss.hasPermission('zc:logistics:update')")
    public CommonResult<Boolean> updateLogistics(@Valid @RequestBody LogisticsSaveReqVO updateReqVO) {
        logisticsService.updateLogistics(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除物流公司")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('zc:logistics:delete')")
    public CommonResult<Boolean> deleteLogistics(@RequestParam("id") Long id) {
        logisticsService.deleteLogistics(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除物流公司")
                @PreAuthorize("@ss.hasPermission('zc:logistics:delete')")
    public CommonResult<Boolean> deleteLogisticsList(@RequestParam("ids") List<Long> ids) {
        logisticsService.deleteLogisticsListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得物流公司")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('zc:logistics:query')")
    public CommonResult<LogisticsRespVO> getLogistics(@RequestParam("id") Long id) {
        LogisticsDO logistics = logisticsService.getLogistics(id);
        return success(BeanUtils.toBean(logistics, LogisticsRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得物流公司分页")
    @PreAuthorize("@ss.hasPermission('zc:logistics:query')")
    public CommonResult<PageResult<LogisticsRespVO>> getLogisticsPage(@Valid LogisticsPageReqVO pageReqVO) {
        PageResult<LogisticsDO> pageResult = logisticsService.getLogisticsPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, LogisticsRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出物流公司 Excel")
    @PreAuthorize("@ss.hasPermission('zc:logistics:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportLogisticsExcel(@Valid LogisticsPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<LogisticsDO> list = logisticsService.getLogisticsPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "物流公司.xls", "数据", LogisticsRespVO.class,
                        BeanUtils.toBean(list, LogisticsRespVO.class));
    }

}