package cn.iocoder.yudao.module.zc.dal.dataobject.curtainstructure;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 窗帘结构部位 DO
 *
 * @author 芋道源码
 */
@TableName("zc_curtain_structure")
@KeySequence("zc_curtain_structure_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurtainStructureDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 部位名称
     */
    private String name;
    /**
     * 帘头/帘身/飘窗垫/其他
     */
    private String type;
    /**
     * 备注
     */
    private String note;


}