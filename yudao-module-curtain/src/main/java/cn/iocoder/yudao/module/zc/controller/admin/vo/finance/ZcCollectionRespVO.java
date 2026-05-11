package cn.iocoder.yudao.module.zc.controller.admin.vo.finance;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 收款单列表/简要（不含分摊明细）
 */
@Data
public class ZcCollectionRespVO {

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

}
