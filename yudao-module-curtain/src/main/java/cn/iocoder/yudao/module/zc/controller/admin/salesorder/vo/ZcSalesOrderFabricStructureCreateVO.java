package cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import javax.validation.Valid;

/**
 * 管理后台 - 面单结构行（嵌套创建/更新）VO
 */
@Schema(description = "管理后台 - 面单结构行（嵌套创建/更新）VO")
@Data
public class ZcSalesOrderFabricStructureCreateVO {

    /**
     * 结构行 ID，整单更新时传入表示更新已有行，不传或为 null 表示新增行
     */
    @Schema(description = "结构行 ID（更新时传入，新增时不传）", example = "20001")
    private Long id;

    /** 用料明细列表 */
    @Schema(description = "用料明细列表")
    @Valid
    private List<ZCSalesOrderMaterialCreateVO> materials;

}
