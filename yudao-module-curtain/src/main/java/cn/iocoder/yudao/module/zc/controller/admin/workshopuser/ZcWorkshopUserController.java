package cn.iocoder.yudao.module.zc.controller.admin.workshopuser;

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

import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.zc.controller.admin.workshopuser.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.workshopuser.ZcWorkshopUserDO;
import cn.iocoder.yudao.module.zc.service.workshopuser.ZcWorkshopUserService;

@Tag(name = "管理后台 - 车间员工")
@RestController
@RequestMapping("/zc/workshop-user")
@Validated
public class ZcWorkshopUserController {

    @Resource
    private ZcWorkshopUserService workshopUserService;

    @PostMapping("/create")
    @Operation(summary = "创建车间员工")
    @PreAuthorize("@ss.hasPermission('zc:workshop-user:create')")
    public CommonResult<Long> createWorkshopUser(@Valid @RequestBody ZcWorkshopUserSaveReqVO createReqVO) {
        return success(workshopUserService.createWorkshopUser(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新车间员工")
    @PreAuthorize("@ss.hasPermission('zc:workshop-user:update')")
    public CommonResult<Boolean> updateWorkshopUser(@Valid @RequestBody ZcWorkshopUserSaveReqVO updateReqVO) {
        workshopUserService.updateWorkshopUser(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除车间员工")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('zc:workshop-user:delete')")
    public CommonResult<Boolean> deleteWorkshopUser(@RequestParam("id") Long id) {
        workshopUserService.deleteWorkshopUser(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除车间员工")
                @PreAuthorize("@ss.hasPermission('zc:workshop-user:delete')")
    public CommonResult<Boolean> deleteWorkshopUserList(@RequestParam("ids") List<Long> ids) {
        workshopUserService.deleteWorkshopUserListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得车间员工")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('zc:workshop-user:query')")
    public CommonResult<ZcWorkshopUserRespVO> getWorkshopUser(@RequestParam("id") Long id) {
        ZcWorkshopUserDO workshopUser = workshopUserService.getWorkshopUser(id);
        return success(BeanUtils.toBean(workshopUser, ZcWorkshopUserRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得车间员工分页")
    @PreAuthorize("@ss.hasPermission('zc:workshop-user:query')")
    public CommonResult<PageResult<ZcWorkshopUserRespVO>> getWorkshopUserPage(@Valid ZcWorkshopUserPageReqVO pageReqVO) {
        PageResult<ZcWorkshopUserDO> pageResult = workshopUserService.getWorkshopUserPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ZcWorkshopUserRespVO.class));
    }

    @GetMapping("/simple-list")
    @Operation(summary = "获得车间员工精简列表", description = "主要用于前端的下拉选项")
    public CommonResult<List<ZcWorkshopUserSimpleRespVO>> getWorkshopUserSimpleList() {
        List<ZcWorkshopUserDO> list = workshopUserService.getWorkshopUserList(
                new ZcWorkshopUserListReqVO().setStatus(CommonStatusEnum.ENABLE.getStatus()));
        return success(convertList(list, item -> new ZcWorkshopUserSimpleRespVO()
                .setId(item.getId())
                .setName(item.getName())));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出车间员工 Excel")
    @PreAuthorize("@ss.hasPermission('zc:workshop-user:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportWorkshopUserExcel(@Valid ZcWorkshopUserPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ZcWorkshopUserDO> list = workshopUserService.getWorkshopUserPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "车间员工.xls", "数据", ZcWorkshopUserRespVO.class,
                        BeanUtils.toBean(list, ZcWorkshopUserRespVO.class));
    }

}