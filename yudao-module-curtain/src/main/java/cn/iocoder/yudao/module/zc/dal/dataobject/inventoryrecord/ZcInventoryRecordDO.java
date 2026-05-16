package cn.iocoder.yudao.module.zc.dal.dataobject.inventoryrecord;

import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 盘点记录 DO
 *
 * @author 芋道源码
 */
@TableName("zc_inventory_record")
@KeySequence("zc_inventory_record_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZcInventoryRecordDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 产品
     */
    private Long productId;
    /**
     * 批次
     */
    private Long batchId;
    /**
     * 盘点前数量
     */
    private BigDecimal oldQuantity;
    /**
     * 盘点后数量
     */
    private BigDecimal newQuantity;
    /**
     * 备注
     */
    private String note;


}