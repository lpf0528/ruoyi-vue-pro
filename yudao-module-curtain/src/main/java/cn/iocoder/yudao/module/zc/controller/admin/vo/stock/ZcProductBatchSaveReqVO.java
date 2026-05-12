package cn.iocoder.yudao.module.zc.controller.admin.vo.stock;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ZcProductBatchSaveReqVO {

    /** 入库后由系统生成，列表/详情展示用 */
    private String batchNo;

    private Long purchaseOrderId;

    @NotNull
    private LocalDate inboundDate;
    @NotNull
    private Long productId;
    @NotNull
    private BigDecimal inboundQuantity;
    private Long warehouseId;
    private Long supplierId;
    private String note;

}
