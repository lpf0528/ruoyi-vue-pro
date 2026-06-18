package cn.iocoder.yudao.module.zc.controller.admin.bills;

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
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.zc.controller.admin.bills.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.bills.ZcBillMethodsDO;
import cn.iocoder.yudao.module.zc.service.bills.ZcBillMethodsService;

@Tag(name = "管理后台 - 收款方式")
@RestController
@RequestMapping("/zc/bill-methods")
@Validated
public class ZcBillMethodsController {

    @Resource
    private ZcBillMethodsService billMethodsService;

    @PostMapping("/create")
    @Operation(summary = "创建收款方式")
    @PreAuthorize("@ss.hasPermission('zc:bill-methods:create')")
    public CommonResult<Long> createBillMethods(@Valid @RequestBody ZcBillMethodsSaveReqVO createReqVO) {
        return success(billMethodsService.createBillMethods(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新收款方式")
    @PreAuthorize("@ss.hasPermission('zc:bill-methods:update')")
    public CommonResult<Boolean> updateBillMethods(@Valid @RequestBody ZcBillMethodsSaveReqVO updateReqVO) {
        billMethodsService.updateBillMethods(updateReqVO);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得收款方式")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('zc:bill-methods:query')")
    public CommonResult<ZcBillMethodsRespVO> getBillMethods(@RequestParam("id") Long id) {
        ZcBillMethodsDO billMethods = billMethodsService.getBillMethods(id);
        return success(BeanUtils.toBean(billMethods, ZcBillMethodsRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得收款方式分页")
    @PreAuthorize("@ss.hasPermission('zc:bill-methods:query')")
    public CommonResult<PageResult<ZcBillMethodsRespVO>> getBillMethodsPage(@Valid ZcBillMethodsPageReqVO pageReqVO) {
        PageResult<ZcBillMethodsDO> pageResult = billMethodsService.getBillMethodsPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ZcBillMethodsRespVO.class));
    }

    @GetMapping("/simple-list")
    @Operation(summary = "获得收款方式精简列表", description = "主要用于前端的下拉选项")
    public CommonResult<List<ZcBillMethodsSimpleRespVO>> getBillMethodsSimpleList() {
        List<ZcBillMethodsDO> list = billMethodsService.getBillMethodsList(new ZcBillMethodsListReqVO());
        return success(convertList(list, item -> new ZcBillMethodsSimpleRespVO()
                .setId(item.getId())
                .setName(item.getName())
                .setGroup(item.getGroup())));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出收款方式 Excel")
    @PreAuthorize("@ss.hasPermission('zc:bill-methods:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportBillMethodsExcel(@Valid ZcBillMethodsPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ZcBillMethodsDO> list = billMethodsService.getBillMethodsPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "收款方式.xls", "数据", ZcBillMethodsRespVO.class,
                        BeanUtils.toBean(list, ZcBillMethodsRespVO.class));
    }

}