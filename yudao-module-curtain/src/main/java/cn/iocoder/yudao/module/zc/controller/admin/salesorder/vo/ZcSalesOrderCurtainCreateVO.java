package cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 管理后台 - 销售订单窗帘行（嵌套创建）VO
 *
 * <p>用于订单整单创建接口中内嵌的窗帘行，
 * orderId 由 Service 层自动填充，无需前端传入。
 * mountings 前端以字符串数组传入，Service 层序列化后存储。</p>
 */
@Schema(description = "管理后台 - 销售订单窗帘行（嵌套创建）VO")
@Data
public class ZcSalesOrderCurtainCreateVO {

    /** 窗帘款式 ID，必填 */
    @Schema(description = "款式", requiredMode = Schema.RequiredMode.REQUIRED, example = "26707")
    @NotNull(message = "窗帘款式不能为空")
    private Long curtainId;

    /** 房间名称 */
    @Schema(description = "房间")
    private String room;

    /** 褶倍快照 */
    @Schema(description = "褶倍快照")
    private BigDecimal pleatRatioValue;

    /** 褶距 */
    @Schema(description = "褶距")
    private BigDecimal pleatsDistance;

    /** 折扣率 */
    @Schema(description = "折扣率")
    private BigDecimal discountRate;

    /** 应收金额 */
    @Schema(description = "应收金额")
    private BigDecimal amount;

    /** 图片1 */
    @Schema(description = "图片1")
    private String image1;

    /** 图片2 */
    @Schema(description = "图片2")
    private String image2;

    /**
     * 配件多选列表（前端传数组，Service 层存为 JSON 字符串）
     *
     * <p>示例：["加铅块", "加磁条"]</p>
     */
    @Schema(description = "配件多选")
    private List<String> mountings;

    /** 备注 */
    @Schema(description = "备注")
    private String note;

    /** 结构列表，至少包含一个 */
    @Schema(description = "结构列表")
    @NotEmpty(message = "结构列表不能为空，至少包含一个结构行")
    @Valid
    private List<ZcSalesOrderStructureCreateVO> structures;

}
