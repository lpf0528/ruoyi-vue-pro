package cn.iocoder.yudao.module.zc.dal.dataobject.processnode;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

import java.util.List;

/**
 * 订单工序记录 DO
 *
 * <p>以流水账形式记录订单每道工序的执行情况，支持附图和备注。
 * node_name 冗余存储工序名快照，防止节点名被修改后历史记录失真。</p>
 *
 * @author 01Coder
 */
@TableName(value = "zc_order_process_record", autoResultMap = true)
@KeySequence("zc_order_process_record_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZcOrderProcessRecordDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 关联销售订单 ID
     */
    private Long orderId;
    /**
     * 工序节点 ID，null 时表示自定义工序
     */
    private Long nodeId;
    /**
     * 工序名称快照，冗余存储，防止节点名变更后历史记录受影响
     */
    private String nodeName;
    /**
     * 状态：1=进行中，2=已完成
     */
    private Integer status;
    /**
     * 操作人员 ID，关联 system_users.id
     */
    private Long operatorUserId;
    /**
     * 备注（质检不通过原因、特殊情况说明等）
     */
    private String note;
    /**
     * 现场照片 URL 列表，JSON 格式存储
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> imageUrls;

}
