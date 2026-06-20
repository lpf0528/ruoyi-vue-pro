package cn.iocoder.yudao.module.zc.controller.admin.customer;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
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

import cn.iocoder.yudao.module.zc.controller.admin.customer.vo.*;
import cn.iocoder.yudao.module.zc.controller.admin.customerbalancelog.vo.ZcCustomerBalanceLogRespVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.customer.ZcCustomerDO;
import cn.iocoder.yudao.module.zc.dal.dataobject.customerbalancelog.ZcCustomerBalanceLogDO;
import cn.iocoder.yudao.module.zc.service.customer.ZcCustomerService;
import cn.iocoder.yudao.module.zc.service.customerbalancelog.ZcCustomerBalanceLogService;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "管理后台 - 客户资料")
@RestController
@RequestMapping("/zc/customer")
@Validated
public class ZcCustomerController {

    @Resource
    private ZcCustomerService customerService;
    @Resource
    private ZcCustomerBalanceLogService customerBalanceLogService;

    @PostMapping("/create")
    @Operation(summary = "创建客户资料")
    @PreAuthorize("@ss.hasPermission('zc:customer:create')")
    public CommonResult<Long> createCustomer(@Valid @RequestBody ZcCustomerSaveReqVO createReqVO) {
        return success(customerService.createCustomer(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新客户资料")
    @PreAuthorize("@ss.hasPermission('zc:customer:update')")
    public CommonResult<Boolean> updateCustomer(@Valid @RequestBody ZcCustomerSaveReqVO updateReqVO) {
        customerService.updateCustomer(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除客户资料")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('zc:customer:delete')")
    public CommonResult<Boolean> deleteCustomer(@RequestParam("id") Long id) {
        customerService.deleteCustomer(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得客户资料")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('zc:customer:query')")
    public CommonResult<ZcCustomerRespVO> getCustomer(@RequestParam("id") Long id) {
        ZcCustomerDO customer = customerService.getCustomer(id);
        return success(BeanUtils.toBean(customer, ZcCustomerRespVO.class));
    }

    @GetMapping("/balance-log/latest-order-confirm")
    @Operation(summary = "获得客户订单确认扣减的最新余额流水")
    @Parameters({
            @Parameter(name = "customerId", description = "客户编号", required = true, example = "7166"),
            @Parameter(name = "refId", description = "关联销售订单主键（zc_sales_order.id）", required = true, example = "1024")
    })
    @PreAuthorize("@ss.hasPermission('zc:customer:query')")
    public CommonResult<ZcCustomerBalanceLogRespVO> getLatestOrderConfirmBalanceLog(
            @RequestParam("customerId") @NotNull Long customerId,
            @RequestParam("refId") @NotNull Long refId) {
        ZcCustomerBalanceLogDO log = customerBalanceLogService.getLatestOrderConfirmLog(customerId, refId);
        return success(BeanUtils.toBean(log, ZcCustomerBalanceLogRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得客户资料分页")
    @PreAuthorize("@ss.hasPermission('zc:customer:query')")
    public CommonResult<PageResult<ZcCustomerRespVO>> getCustomerPage(@Valid ZcCustomerPageReqVO pageReqVO) {
        return success(customerService.getCustomerPage(pageReqVO));
    }

    @GetMapping("/simple-list")
    @Operation(summary = "获得客户资料精简列表", description = "主要用于前端的下拉选项")
    public CommonResult<List<ZcCustomerSimpleRespVO>> getCustomerSimpleList(
            @RequestParam(value = "shortName", required = false) String shortName) {
        List<ZcCustomerDO> list = customerService.getCustomerList(
                new ZcCustomerListReqVO().setShortName(shortName));
        return success(convertList(list, item -> new ZcCustomerSimpleRespVO()
                .setId(item.getId())
                .setShortName(item.getShortName())
                .setName(item.getName())
                .setContactName(item.getContactName())
                .setAddress(item.getAddress())
                .setDeliveryAddress(item.getDeliveryAddress())
                .setMobile(item.getMobile())
                .setMobile2(item.getMobile2())
                .setLogisticId(item.getLogisticId())
                .setBrandId(item.getBrandId())
                .setBalance(item.getBalance())));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出客户资料 Excel")
    @PreAuthorize("@ss.hasPermission('zc:customer:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportCustomerExcel(@Valid ZcCustomerPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ZcCustomerRespVO> list = customerService.getCustomerPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "客户资料.xls", "数据", ZcCustomerRespVO.class, list);
    }

    @GetMapping("/get-import-template")
    @Operation(summary = "下载客户资料导入模板")
    @PreAuthorize("@ss.hasPermission('zc:customer:import')")
    public void importTemplate(HttpServletResponse response) throws IOException {
        // 构造示例数据，帮助用户了解各列格式
        List<ZcCustomerImportExcelVO> list = Arrays.asList(
                ZcCustomerImportExcelVO.builder()
                        .shortName("示例客户A").name("示例客户A有限公司")
                        .contactName("张三")
                        .province("广东省").city("广州市").district("天河区")
                        .deliveryAddress("XX路1号")
                        .mobile("13800138000").mobile2("13900139000")
                        .logisticName("顺丰速运").brandName("品牌名称示例")
                        .note("备注信息示例").build(),
                ZcCustomerImportExcelVO.builder()
                        .shortName("示例客户B").name("示例客户B贸易公司")
                        .contactName("李四")
                        .province("广东省").city("深圳市").district("南山区")
                        .deliveryAddress("XX街2号")
                        .mobile("13700137000").mobile2("")
                        .logisticName("").brandName("")
                        .note("").build()
        );
        ExcelUtils.write(response, "客户资料导入模板.xls", "客户列表", ZcCustomerImportExcelVO.class, list);
    }

    @PostMapping("/import-excel")
    @Operation(summary = "导入客户资料 Excel")
    @Parameters({
            @Parameter(name = "file", description = "Excel 文件", required = true),
            @Parameter(name = "updateSupport", description = "是否支持更新已存在的客户，默认 false", example = "false")
    })
    @PreAuthorize("@ss.hasPermission('zc:customer:import')")
    @ApiAccessLog(operateType = IMPORT)
    public CommonResult<ZcCustomerImportRespVO> importExcel(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "updateSupport", required = false, defaultValue = "false") Boolean updateSupport)
            throws Exception {
        List<ZcCustomerImportExcelVO> list = ExcelUtils.read(file, ZcCustomerImportExcelVO.class);
        return success(customerService.importCustomerList(list, updateSupport));
    }

}
