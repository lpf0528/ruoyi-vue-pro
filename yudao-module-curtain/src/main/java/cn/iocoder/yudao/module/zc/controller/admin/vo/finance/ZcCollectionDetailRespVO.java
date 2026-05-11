package cn.iocoder.yudao.module.zc.controller.admin.vo.finance;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class ZcCollectionDetailRespVO {

    private Long id;
    private String collectionNo;
    private LocalDate collectionDate;
    private Long collectionerId;
    private Long customerId;
    private BigDecimal amount;
    private BigDecimal discountAmount;
    private Long paymentId;
    private String image1;
    private String image2;
    private String note;

    private List<AllocItem> allocs;

    @Data
    public static class AllocItem {
        private Long orderId;
        private BigDecimal payAmount;
    }

}
