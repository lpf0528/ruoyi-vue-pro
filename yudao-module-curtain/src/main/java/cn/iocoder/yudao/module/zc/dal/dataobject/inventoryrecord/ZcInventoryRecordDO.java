package cn.iocoder.yudao.module.zc.dal.dataobject.inventoryrecord;

import lombok.*;
import java.math.BigDecimal;
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
    /**
     * 变化数量（new_quantity - old_quantity），正数表示增加，负数表示减少
     */
    private BigDecimal changeQuantity;
    /**
     * 操作类型，参见 {@link cn.iocoder.yudao.module.zc.enums.ZcInventoryRecordOperateEnum}
     * 取值：PANDIAN / RUKU / CAIJIAN / CANCEL_CAIJIAN
     */
    private String operate;
    /**
     * 关联订单 ID，裁剪/撤销裁剪时记录来源订单；盘点/入库时为 null
     */
    private Long orderId;

}