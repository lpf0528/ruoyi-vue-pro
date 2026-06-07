package cn.iocoder.yudao.module.zc.dal.dataobject.curtaininstallprocess;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 安装工艺 DO
 *
 * @author 01Coder
 */
@TableName(value = "zc_curtain_install_process", autoResultMap = true)
@KeySequence("zc_curtain_install_process_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZcCurtainInstallProcessDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 工艺名称
     */
    private String name;
    /**
     * 备注
     */
    private String note;
    /**
     * 关联工序节点 ID 列表，存 JSON 数组，如 [1, 2, 3]
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<Long> nodeIds;


}