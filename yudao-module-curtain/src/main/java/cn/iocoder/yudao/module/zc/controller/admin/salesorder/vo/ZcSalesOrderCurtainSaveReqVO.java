package cn.iocoder.yudao.module.zc.controller.admin.salesorder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 成品订单-窗帘行新增/修改 Request VO")
@Data
public class ZcSalesOrderCurtainSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "3946")
    private Long id;

    @Schema(description = "销售单", requiredMode = Schema.RequiredMode.REQUIRED, example = "13858")
    @NotNull(message = "销售单不能为空")
    private Long orderId;

    @Schema(description = "款式", example = "26707")
    private Long curtainId;

    @Schema(description = "房间")
    private String room;

    @Schema(description = "褶倍快照")
    private BigDecimal pleatRatioValue;

    @Schema(description = "折扣率")
    private BigDecimal discountRate;

    @Schema(description = "应收金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "应收金额不能为空")
    private BigDecimal amount;

    @Schema(description = "图片1")
    private String image1;

    @Schema(description = "图片2")
    private String image2;

    @Schema(description = "配件多选")
    private String mountings;

    @Schema(description = "备注")
    private String note;

}