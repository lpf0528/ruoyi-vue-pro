package cn.iocoder.yudao.module.zc.dal.dataobject.customerbalancelog;

import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 客户余额变动流水 DO
 *
 * @author 01Coder
 */
@TableName("zc_customer_balance_log")
@KeySequence("zc_customer_balance_log_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZcCustomerBalanceLogDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 客户
     */
    private Long customerId;
    /**
     * 余额变动额
     */
    private BigDecimal changeAmount;
    /**
     * 变动前余额
     */
    private BigDecimal balanceBefore;
    /**
     * 变动后余额
     */
    private BigDecimal balanceAfter;
    /**
     * 业务类型
     *
     * 枚举 {@link TODO zc_customer_balance_biz_type 对应的类}
     */
    private String bizType;
    /**
     * 关联单据类型
     */
    private String refType;
    /**
     * 关联单据主键
     */
    private Long refId;
    /**
     * 关联单号快照
     */
    private String refNo;
    /**
     * 备注
     */
    private String remark;


}