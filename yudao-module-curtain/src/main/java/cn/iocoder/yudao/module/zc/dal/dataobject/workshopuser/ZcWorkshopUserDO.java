package cn.iocoder.yudao.module.zc.dal.dataobject.workshopuser;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 车间员工 DO
 *
 * @author 01Coder
 */
@TableName(value = "zc_workshop_user", autoResultMap = true)
@KeySequence("zc_workshop_user_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZcWorkshopUserDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 状态，参见 {@link cn.iocoder.yudao.module.zc.enums.ZcWorkshopUserStatusEnum}；0=关闭，1=开启
     */
    private Integer status;

    /**
     * 工序节点 IDs，关联 ZcProcessNodeDO，存 JSON 数组
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<Long> nodeIds;

}