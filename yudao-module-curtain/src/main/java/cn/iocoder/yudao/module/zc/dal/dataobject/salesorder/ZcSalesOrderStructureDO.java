package cn.iocoder.yudao.module.zc.dal.dataobject.salesorder;

import cn.iocoder.yudao.module.zc.enums.ZcOpenMethodEnum;
import cn.iocoder.yudao.module.zc.enums.ZcPasteDirectionEnum;
import cn.iocoder.yudao.module.zc.enums.ZcProcessTypeEnum;
import lombok.*;
import java.math.BigDecimal;
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
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private BigDecimal height;
    /**
     * 宽
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private BigDecimal width;
    /**
     * 左转角
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private String leftCorner;
    /**
     * 右转角
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private String rightCorner;
    /**
     * 粘贴方向
     * 枚举 {@link ZcPasteDirectionEnum}，字典类型 {@code zc_paste_direction}
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private String pasteDirection;
    /**
     * 安装工艺
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private Long installProcessId;
    /**
     * 打开方式
     * 枚举 {@link ZcOpenMethodEnum}，字典类型 {@code zc_open_method}
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private String openMethod;
    /**
     * 加工类型
     * 枚举 {@link ZcProcessTypeEnum}，字典类型 {@code zc_process_type}
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private String processType;
    /**
     * 是否定型；数据库 NOT NULL DEFAULT 0，null 在 Service 层归一为 false
     */
    private Boolean isShaping;
    /**
     * 总褶数
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private Integer pleatsNum;
    /**
     * 褶距
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private BigDecimal pleatsDistance;
    /**
     * 裙摆高度
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private BigDecimal skirtHeight;
    /**
     * 备注
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private String note;


}