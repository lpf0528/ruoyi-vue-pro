package cn.iocoder.yudao.module.zc.dal.dataobject.logistics;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 物流公司 DO
 *
 * @author 芋道源码
 */
@TableName("zc_logistics")
@KeySequence("zc_logistics_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogisticsDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 编码，例如：shunfeng
     */
    private String code;
    /**
     * 名称，例如：顺丰快递
     */
    private String name;
    /**
     * 联系人
     */
    private String contactName;
    /**
     * 电话
     */
    private String mobile;
    /**
     * 地址
     */
    private String address;
    /**
     * 备注
     */
    private String note;


}