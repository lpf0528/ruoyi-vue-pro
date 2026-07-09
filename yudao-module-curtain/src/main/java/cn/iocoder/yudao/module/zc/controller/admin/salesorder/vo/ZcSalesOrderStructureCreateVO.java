package cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * 管理后台 - 销售订单结构行（嵌套创建/更新）VO
 *
 * <p>用于订单整单创建/更新接口中内嵌的结构行。
 * 整单更新时：有 id 的行执行 UPDATE，无 id 的行执行 INSERT，不在请求中的行执行 DELETE。
 * orderId / orderCurtainId 由 Service 层根据父级 ID 自动填充，无需前端传入。</p>
 */
@Schema(description = "管理后台 - 销售订单结构行（嵌套创建/更新）VO")
@Data
public class ZcSalesOrderStructureCreateVO {

    /**
     * 结构行 ID，整单更新时传入表示更新已有行，不传或为 null 表示新增行
     */
    @Schema(description = "结构行 ID（更新时传入，新增时不传）", example = "20001")
    private Long id;

    /** 结构款式 ID，必填 */
    @Schema(description = "结构", requiredMode = Schema.RequiredMode.REQUIRED, example = "17209")
    @NotNull(message = "结构不能为空")
    private Long structureId;

    /** 高度（米） */
    @Schema(description = "高")
    private BigDecimal height;

    /** 宽度（米） */
    @Schema(description = "宽")
    private BigDecimal width;

    /** 左转角 */
    @Schema(description = "左转角")
    private String leftCorner;

    /** 右转角 */
    @Schema(description = "右转角")
    private String rightCorner;

    /** 粘贴方向 */
    @Schema(description = "粘贴方向")
    private String pasteDirection;

    /** 安装工艺 */
    @Schema(description = "安装工艺", example = "5095")
    private Long installProcessId;

    /** 打开方式 */
    @Schema(description = "打开方式")
    private String openMethod;

    /** 加工类型 */
    @Schema(description = "加工类型", example = "DKMG")
    private String processType;

    /** 是否定型 */
    @Schema(description = "是否定型")
    private Boolean isShaping;

    /** 总褶数 */
    @Schema(description = "总褶数")
    private Integer pleatsNum;

    /** 褶距 */
    @Schema(description = "褶距")
    private BigDecimal pleatsDistance;

    /** 裙摆高度 */
    @Schema(description = "裙摆高度")
    private BigDecimal skirtHeight;

    /** 备注 */
    @Schema(description = "备注")
    private String note;

    /** 用料明细列表 */
    @Schema(description = "用料明细列表")
    @Valid
    private List<ZCSalesOrderMaterialCreateVO> materials;

}
