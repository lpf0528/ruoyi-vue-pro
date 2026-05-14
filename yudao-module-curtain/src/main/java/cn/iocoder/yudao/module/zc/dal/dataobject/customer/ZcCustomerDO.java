package cn.iocoder.yudao.module.zc.dal.dataobject.customer;

import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 客户资料 DO
 *
 * @author 芋道源码
 */
@TableName("zc_customer")
@KeySequence("zc_customer_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZcCustomerDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 简称
     */
    private String shortName;
    /**
     * 全称
     */
    private String name;
    /**
     * 联系人
     */
    private String contactName;
    /**
     * 固定地址
     */
    private String address;
    /**
     * 省份
     */
    private String province;
    /**
     * 市区
     */
    private String city;
    /**
     * 县区
     */
    private String district;
    /**
     * 送货地址
     */
    private String deliveryAddress;
    /**
     * 手机
     */
    private String mobile;
    /**
     * 联系电话
     */
    private String mobile2;
    /**
     * 物流
     */
    private Long logisticId;
    /**
     * 关联品牌
     */
    private Long brandId;
    /**
     * 账户余额
     */
    private BigDecimal balance;
    /**
     * 备注
     */
    private String note;


}