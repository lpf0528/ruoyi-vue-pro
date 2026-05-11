package cn.iocoder.yudao.module.zc.controller.admin.vo.sales;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "管理后台 - 智仓销售订单")
@Data
public class ZcSalesOrderRespVO {

    private Long id;
    private String orderNo;
    private Long customerId;
    private String mobile;
    private Long brandId;
    private String category;
    private LocalDate orderDate;
    private Long logisticId;
    private String receiver;
    private String deliveryAddress;
    private BigDecimal freight;
    private String types;
    private BigDecimal amount;
    private BigDecimal amountReceived;
    private LocalDate deliveryDate;
    private String payStatus;
    private String confirmStatus;
    private LocalDateTime confirmTime;
    private Boolean isExpedited;
    private String note;

    private List<ZcSalesOrderSaveReqVO.ZcSalesOrderCurtainSaveVO> curtains;
    private List<ZcSalesOrderSaveReqVO.ZcSalesOrderProductLineSaveVO> fabricLines;

}
