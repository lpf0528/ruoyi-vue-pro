package cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import javax.validation.Valid;

/**
 * 管理后台 - 面单窗帘行（嵌套创建/更新）VO
 */
@Schema(description = "管理后台 - 面单窗帘行（嵌套创建/更新）VO")
@Data
public class ZcSalesOrderFabricCurtainCreateVO {

    /**
     * 窗帘行 ID，整单更新时传入表示更新已有行，不传或为 null 表示新增行
     */
    @Schema(description = "窗帘行 ID（更新时传入，新增时不传）", example = "10001")
    private Long id;

    /** 应收金额 */
    @Schema(description = "应收金额")
    private BigDecimal amount;

    /** 备注 */
    @Schema(description = "备注")
    private String note;

    /** 结构列表 */
    @Schema(description = "结构列表")
    @Valid
    private List<ZcSalesOrderFabricStructureCreateVO> structures;

}
