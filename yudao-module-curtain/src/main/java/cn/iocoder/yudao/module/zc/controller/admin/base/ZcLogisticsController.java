package cn.iocoder.yudao.module.zc.controller.admin.base;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.zc.controller.admin.vo.base.ZcLogisticsPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.base.ZcLogisticsSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.base.ZcLogisticsDO;
import cn.iocoder.yudao.module.zc.service.base.ZcLogisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 智仓物流")
@RestController
@RequestMapping("/zc/logistics")
@Validated
public class ZcLogisticsController {

    @Resource
    private ZcLogisticsService logisticsService;

    @PostMapping("/create")
    @Operation(summary = "创建物流")
    @PreAuthorize("@ss.hasPermission('zc:logistics:create')")
    public CommonResult<Long> create(@Valid @RequestBody ZcLogisticsSaveReqVO reqVO) {
        return success(logisticsService.create(reqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新物流")
    @PreAuthorize("@ss.hasPermission('zc:logistics:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody ZcLogisticsSaveReqVO reqVO) {
        logisticsService.update(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除物流")
    @PreAuthorize("@ss.hasPermission('zc:logistics:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        logisticsService.delete(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得物流")
    @PreAuthorize("@ss.hasPermission('zc:logistics:query')")
    public CommonResult<ZcLogisticsSaveReqVO> get(@RequestParam("id") Long id) {
        return success(BeanUtils.toBean(logisticsService.get(id), ZcLogisticsSaveReqVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "物流分页")
    @PreAuthorize("@ss.hasPermission('zc:logistics:query')")
    public CommonResult<PageResult<ZcLogisticsSaveReqVO>> page(@Valid ZcLogisticsPageReqVO pageReqVO) {
        PageResult<ZcLogisticsDO> pageResult = logisticsService.getPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ZcLogisticsSaveReqVO.class));
    }

}
