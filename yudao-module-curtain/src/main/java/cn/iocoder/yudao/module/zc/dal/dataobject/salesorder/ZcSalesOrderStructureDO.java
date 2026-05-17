package cn.iocoder.yudao.module.zc.dal.dataobject.salesorder;

import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 成品订单-结构 DO
 *
 * @author 01Coder
 */
@TableName("zc_sales_order_structure")
@KeySequence("zc_sales_order_structure_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZcSalesOrderStructureDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 销售单
     */
    private Long orderId;
    /**
     * 窗帘行
     */
    private Long orderCurtainId;
    /**
     * 结构
     */
    private Long structureId;
    /**
     * 高
     */
    private BigDecimal height;
    /**
     * 宽
     */
    private BigDecimal width;
    /**
     * 左转角
     */
    private String leftCorner;
    /**
     * 右转角
     */
    private String rightCorner;
    /**
     * 粘贴方向
     */
    private String pasteDirection;
    /**
     * 安装工艺
     */
    private Long installProcessId;
    /**
     * 打开方式
     */
    private String openMethod;
    /**
     * 加工类型
     */
    private String processType;
    /**
     * 是否定型
     */
    private Boolean shaping;
    /**
     * 总褶数
     */
    private Integer pleatsNum;
    /**
     * 褶距
     */
    private BigDecimal pleatsDistance;
    /**
     * 裙摆高度
     */
    private BigDecimal skirtHeight;
    /**
     * 备注
     */
    private String note;


}