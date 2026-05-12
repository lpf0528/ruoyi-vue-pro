package cn.iocoder.yudao.module.zc.controller.admin.curtain;

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

import cn.iocoder.yudao.module.zc.controller.admin.curtain.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.curtain.CurtainDO;
import cn.iocoder.yudao.module.zc.service.curtain.CurtainService;

@Tag(name = "管理后台 - 窗帘")
@RestController
@RequestMapping("/zc/curtain")
@Validated
public class CurtainController {

    @Resource
    private CurtainService curtainService;

    @PostMapping("/create")
    @Operation(summary = "创建窗帘")
    @PreAuthorize("@ss.hasPermission('zc:curtain:create')")
    public CommonResult<Long> createCurtain(@Valid @RequestBody CurtainSaveReqVO createReqVO) {
        return success(curtainService.createCurtain(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新窗帘")
    @PreAuthorize("@ss.hasPermission('zc:curtain:update')")
    public CommonResult<Boolean> updateCurtain(@Valid @RequestBody CurtainSaveReqVO updateReqVO) {
        curtainService.updateCurtain(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除窗帘")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('zc:curtain:delete')")
    public CommonResult<Boolean> deleteCurtain(@RequestParam("id") Long id) {
        curtainService.deleteCurtain(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除窗帘")
                @PreAuthorize("@ss.hasPermission('zc:curtain:delete')")
    public CommonResult<Boolean> deleteCurtainList(@RequestParam("ids") List<Long> ids) {
        curtainService.deleteCurtainListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得窗帘")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('zc:curtain:query')")
    public CommonResult<CurtainRespVO> getCurtain(@RequestParam("id") Long id) {
        CurtainDO curtain = curtainService.getCurtain(id);
        return success(BeanUtils.toBean(curtain, CurtainRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得窗帘分页")
    @PreAuthorize("@ss.hasPermission('zc:curtain:query')")
    public CommonResult<PageResult<CurtainRespVO>> getCurtainPage(@Valid CurtainPageReqVO pageReqVO) {
        PageResult<CurtainDO> pageResult = curtainService.getCurtainPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, CurtainRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出窗帘 Excel")
    @PreAuthorize("@ss.hasPermission('zc:curtain:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportCurtainExcel(@Valid CurtainPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<CurtainDO> list = curtainService.getCurtainPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "窗帘.xls", "数据", CurtainRespVO.class,
                        BeanUtils.toBean(list, CurtainRespVO.class));
    }

}