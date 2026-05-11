package cn.iocoder.yudao.module.zc.controller.admin.vo.stock;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class ZcProductBatchDeductReqVO {

    @NotNull
    private Long batchId;
    @NotNull
    private BigDecimal quantity;

}
