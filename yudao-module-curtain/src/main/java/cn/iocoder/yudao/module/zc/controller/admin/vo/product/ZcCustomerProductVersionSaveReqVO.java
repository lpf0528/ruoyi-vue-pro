package cn.iocoder.yudao.module.zc.controller.admin.vo.product;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class ZcCustomerProductVersionSaveReqVO {

    private Long id;
    @NotNull
    private Long customerId;
    @NotNull
    private Long productVersionId;
    private BigDecimal authorizedPrice;

}
