package cn.iocoder.yudao.module.zc.dal.dataobject.customerbalancelog;

import lombok.*;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import cn.iocoder.yudao.module.zc.enums.ZcCustomerBalanceBizTypeEnum;
import cn.iocoder.yudao.module.zc.enums.ZcRefTypeEnum;

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
     * 枚举 {@link ZcCustomerBalanceBizTypeEnum}，字典类型 {@code zc_customer_balance_biz_type}
     */
    private String bizType;
    /**
     * 关联单据类型
     * 枚举 {@link ZcRefTypeEnum}，字典类型 {@code zc_ref_type}
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
