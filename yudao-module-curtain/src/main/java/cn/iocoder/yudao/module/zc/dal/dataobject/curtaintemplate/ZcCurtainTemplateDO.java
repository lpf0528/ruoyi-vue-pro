package cn.iocoder.yudao.module.zc.dal.dataobject.curtaintemplate;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 窗帘模板 DO
 *
 * @author 01Coder
 */
@TableName("zc_curtain_template")
@KeySequence("zc_curtain_template_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZcCurtainTemplateDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 款式
     */
    private Long curtainId;
    /**
     * 结构
     */
    private Long structureId;
    /**
     * 配件
     */
    private Long elementId;
    /**
     * 产品，可为空（模板未指定具体产品时）
     */
    private Long productId;

    /**
     * 产品名称（JOIN 查询冗余字段，非数据库列）
     */
    @TableField(exist = false)
    private String productName;

}