package cn.iocoder.yudao.module.zc.controller.admin.vo.stock;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class ZcInventoryRecordSaveReqVO {

    @NotNull
    private Long productId;
    @NotNull
    private Long batchId;
    @NotNull
    private BigDecimal newQuantity;
    private String note;

}
