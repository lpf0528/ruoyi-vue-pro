package cn.iocoder.yudao.module.zc.dal.dataobject.productbatch;

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
 * 产品批次 DO
 *
 * @author 01Coder
 */
@TableName("zc_product_batch")
@KeySequence("zc_product_batch_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZcProductBatchDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 批号
     */
    private String batchNo;
    /**
     * 入库日期
     */
    private LocalDate inboundDate;
    /**
     * 产品
     */
    private Long productId;
    /**
     * 进货价
     */
    private BigDecimal inboundPrice;
    /**
     * 入库数量
     */
    private BigDecimal inboundQuantity;
    /**
     * 剩余数量
     */
    private BigDecimal quantity;
    /**
     * 仓库
     */
    private Long warehouseId;
    /**
     * 供应商
     */
    private Long supplierId;
    /**
     * 备注
     */
    private String note;


}