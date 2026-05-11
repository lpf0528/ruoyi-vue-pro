package cn.iocoder.yudao.module.zc.controller.admin.vo.sales;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Schema(description = "管理后台 - 智仓销售订单保存")
@Data
public class ZcSalesOrderSaveReqVO {

    @Schema(description = "编号，更新时必填")
    private Long id;

    @NotNull(message = "客户不能为空")
    private Long customerId;

    private String mobile;
    private Long brandId;
    private String category;
    @NotNull(message = "下单日期不能为空")
    private LocalDate orderDate;
    private Long logisticId;
    private String receiver;
    @NotEmpty(message = "送货地址不能为空")
    private String deliveryAddress;
    private BigDecimal freight;

    @NotEmpty(message = "订单类型不能为空")
    private String types;

    private BigDecimal amount;
    private LocalDate deliveryDate;
    private Boolean isExpedited;
    private String note;

    @Valid
    private List<ZcSalesOrderCurtainSaveVO> curtains;

    @Valid
    private List<ZcSalesOrderProductLineSaveVO> fabricLines;

    @Data
    public static class ZcSalesOrderCurtainSaveVO {
        private Long id;
        private Long curtainId;
        private String room;
        private BigDecimal pleatRatioValue;
        private BigDecimal discountRate;
        private BigDecimal amount;
        private String image1;
        private String image2;
        private String mountings;
        private String note;
        @Valid
        private List<ZcSalesOrderStructureSaveVO> structures;
    }

    @Data
    public static class ZcSalesOrderStructureSaveVO {
        private Long id;
        private Long structureId;
        private BigDecimal height;
        private BigDecimal width;
        private String leftCorner;
        private String rightCorner;
        private String pasteDirection;
        private Long installProcessId;
        private String openMethod;
        private String processType;
        private Boolean isShaping;
        private Integer pleatsNum;
        private BigDecimal pleatsDistance;
        private BigDecimal skirtHeight;
        private String note;
        @Valid
        private List<ZcSalesOrderElementSaveVO> elements;
    }

    @Data
    public static class ZcSalesOrderElementSaveVO {
        private Long id;
        private Long elementId;
        private Long productId;
        private Long batchId;
        private BigDecimal price;
        private BigDecimal quantity;
        private String unitValue;
        private BigDecimal discountRate;
        private BigDecimal amount;
        private String note;
    }

    @Data
    public static class ZcSalesOrderProductLineSaveVO {
        private Long id;
        private Long productId;
        private Long batchId;
        private BigDecimal quantity;
        private BigDecimal price;
        private BigDecimal amount;
        private BigDecimal discountRate;
        private String note;
    }

}
