package cn.iocoder.yudao.module.zc.controller.admin.finance;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.zc.controller.admin.vo.finance.ZcCollectionDetailRespVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.finance.ZcCollectionPageReqVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.finance.ZcCollectionRespVO;
import cn.iocoder.yudao.module.zc.controller.admin.vo.finance.ZcCollectionSaveReqVO;
import cn.iocoder.yudao.module.zc.dal.dataobject.finance.ZcCollectionRecordDO;
import cn.iocoder.yudao.module.zc.service.finance.ZcCollectionRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 智仓收款")
@RestController
@RequestMapping("/zc/collection-record")
@Validated
public class ZcCollectionRecordController {

    @Resource
    private ZcCollectionRecordService collectionRecordService;

    @PostMapping("/create")
    @Operation(summary = "创建收款单")
    @PreAuthorize("@ss.hasPermission('zc:collection:create')")
    public CommonResult<Long> createCollection(@Valid @RequestBody ZcCollectionSaveReqVO createReqVO) {
        return success(collectionRecordService.createCollection(createReqVO));
    }

    @GetMapping("/get")
    @Operation(summary = "获得收款单（含分摊明细）")
    @PreAuthorize("@ss.hasPermission('zc:collection:query')")
    public CommonResult<ZcCollectionDetailRespVO> getCollection(@RequestParam("id") Long id) {
        return success(collectionRecordService.getCollectionDetail(id));
    }

    @GetMapping("/page")
    @Operation(summary = "收款分页")
    @PreAuthorize("@ss.hasPermission('zc:collection:query')")
    public CommonResult<PageResult<ZcCollectionRespVO>> getCollectionPage(@Valid ZcCollectionPageReqVO pageReqVO) {
        PageResult<ZcCollectionRecordDO> pageResult = collectionRecordService.getCollectionPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ZcCollectionRespVO.class));
    }

}
