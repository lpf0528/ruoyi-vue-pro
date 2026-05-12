package cn.iocoder.yudao.module.zc.controller.admin.vo.stock;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class ZcPurchaseOrderSaveReqVO {

    private Long id;

    @NotNull
    private LocalDate inboundDate;
    private Long supplierId;
    private String inboundType;
    private Long operatorId;
    private String poNo;
    private String note;

}
