package cn.iocoder.yudao.module.zc.dal.dataobject.finance;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@TableName("zc_collection_record")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZcCollectionRecordDO extends TenantBaseDO {

    @TableId
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
