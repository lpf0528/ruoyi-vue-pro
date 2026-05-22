package cn.iocoder.yudao.module.zc.controller.admin.processnode;

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

import cn.iocoder.yudao.module.zc.controller.admin.processnode.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.processnode.ZcProcessNodeDO;
import cn.iocoder.yudao.module.zc.service.processnode.ZcProcessNodeService;

@Tag(name = "管理后台 - 工序节点配置")
@RestController
@RequestMapping("/zc/process-node")
@Validated
public class ZcProcessNodeController {

    @Resource
    private ZcProcessNodeService processNodeService;

    @PostMapping("/create")
    @Operation(summary = "创建工序节点配置")
    @PreAuthorize("@ss.hasPermission('zc:process-node:create')")
    public CommonResult<Long> createProcessNode(@Valid @RequestBody ZcProcessNodeSaveReqVO createReqVO) {
        return success(processNodeService.createProcessNode(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新工序节点配置")
    @PreAuthorize("@ss.hasPermission('zc:process-node:update')")
    public CommonResult<Boolean> updateProcessNode(@Valid @RequestBody ZcProcessNodeSaveReqVO updateReqVO) {
        processNodeService.updateProcessNode(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除工序节点配置")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('zc:process-node:delete')")
    public CommonResult<Boolean> deleteProcessNode(@RequestParam("id") Long id) {
        processNodeService.deleteProcessNode(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除工序节点配置")
                @PreAuthorize("@ss.hasPermission('zc:process-node:delete')")
    public CommonResult<Boolean> deleteProcessNodeList(@RequestParam("ids") List<Long> ids) {
        processNodeService.deleteProcessNodeListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得工序节点配置")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('zc:process-node:query')")
    public CommonResult<ZcProcessNodeRespVO> getProcessNode(@RequestParam("id") Long id) {
        ZcProcessNodeDO processNode = processNodeService.getProcessNode(id);
        return success(BeanUtils.toBean(processNode, ZcProcessNodeRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得工序节点配置分页")
    @PreAuthorize("@ss.hasPermission('zc:process-node:query')")
    public CommonResult<PageResult<ZcProcessNodeRespVO>> getProcessNodePage(@Valid ZcProcessNodePageReqVO pageReqVO) {
        PageResult<ZcProcessNodeDO> pageResult = processNodeService.getProcessNodePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ZcProcessNodeRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出工序节点配置 Excel")
    @PreAuthorize("@ss.hasPermission('zc:process-node:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportProcessNodeExcel(@Valid ZcProcessNodePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ZcProcessNodeDO> list = processNodeService.getProcessNodePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "工序节点配置.xls", "数据", ZcProcessNodeRespVO.class,
                        BeanUtils.toBean(list, ZcProcessNodeRespVO.class));
    }

}