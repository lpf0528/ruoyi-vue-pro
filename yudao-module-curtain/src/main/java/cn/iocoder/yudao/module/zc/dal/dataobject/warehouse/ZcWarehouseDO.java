package cn.iocoder.yudao.module.zc.dal.dataobject.warehouse;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 仓库 DO
 *
 * @author 芋道源码
 */
@TableName("zc_warehouse")
@KeySequence("zc_warehouse_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZcWarehouseDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 仓库名称
     */
    private String name;
    /**
     * 负责人
     */
    private Long managerId;
    /**
     * 备注
     */
    private String note;
    /**
     * 是否默认仓库，同一时刻至多一条记录为 true
     */
    private Boolean defaultStatus;

}