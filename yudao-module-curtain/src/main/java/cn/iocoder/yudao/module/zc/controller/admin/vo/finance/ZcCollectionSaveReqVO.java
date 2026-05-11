package cn.iocoder.yudao.module.zc.controller.admin.vo.finance;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class ZcCollectionSaveReqVO {

    private Long id;

    @NotNull
    private LocalDate collectionDate;
    private Long collectionerId;
    @NotNull
    private Long customerId;
    @NotNull
    private BigDecimal amount;
    private BigDecimal discountAmount;
    private Long paymentId;
    private String image1;
    private String image2;
    private String note;

    @NotEmpty
    @Valid
    private List<AllocItem> allocs;

    @Data
    public static class AllocItem {
        @NotNull
        private Long orderId;
        @NotNull
        private BigDecimal payAmount;
    }

}
