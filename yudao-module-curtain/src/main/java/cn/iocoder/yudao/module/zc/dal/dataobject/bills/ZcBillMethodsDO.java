package cn.iocoder.yudao.module.zc.dal.dataobject.bills;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 收款方式 DO
 *
 * @author 01Coder
 */
@TableName("zc_bill_methods")
@KeySequence("zc_bill_methods_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZcBillMethodsDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 卡号
     */
    private String cardNo;
    /**
     * 备注
     */
    private String note;
    /**
     * 分组：0=系统配置，1=手工配置
     *
     * <p>group 为 MySQL 保留字，使用 @TableField 显式映射列名</p>
     */
    @TableField("`group`")
    private Integer group;

}