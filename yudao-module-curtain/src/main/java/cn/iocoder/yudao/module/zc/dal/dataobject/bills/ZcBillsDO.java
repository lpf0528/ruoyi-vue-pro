package cn.iocoder.yudao.module.zc.dal.dataobject.bills;

import lombok.*;

import java.time.LocalDate;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 收支账单 DO
 *
 * @author 01Coder
 */
@TableName("zc_bills")
@KeySequence("zc_bills_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZcBillsDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 单号
     */
    private String billNo;
    /**
     * 付款时间
     */
    private LocalDate billDate;
    /**
     * 财务人员
     */
    private Long billUserId;
    /**
     * 客户
     */
    private Long customerId;
    /**
     * 优惠金额
     */
    private BigDecimal discountAmount;
    /**
     * 实收金额 
     */
    private BigDecimal actualAmount;
    /**
     * 收支方式
     */
    private Long billMethodId;
    /**
     * 备注
     */
    private String note;


}