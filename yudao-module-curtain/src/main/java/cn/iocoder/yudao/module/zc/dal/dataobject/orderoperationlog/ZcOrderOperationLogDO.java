package cn.iocoder.yudao.module.zc.dal.dataobject.orderoperationlog;

import lombok.*;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import cn.iocoder.yudao.module.zc.enums.ZcOrderOperateTargetTypeEnum;
import cn.iocoder.yudao.module.zc.enums.ZcOrderOperateTypeEnum;

/**
 * 销售订单操作记录 DO
 *
 * <p>记录订单生命周期内的所有关键操作，包括确认、打包、发货、裁剪配料等，
 * 操作可发生在订单、窗帘行、用料明细三个层级</p>
 *
 * @author 01Coder
 */
@TableName("zc_order_operation_log")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZcOrderOperationLogDO extends BaseDO {

    /** 主键 */
    @TableId
    private Long id;

    /** 销售订单 ID */
    private Long orderId;

    /** 订单号（冗余快照，便于列表展示无需关联查询） */
    private String orderNo;

    /**
     * 操作类型，参见 {@link ZcOrderOperateTypeEnum}
     * 取值：CONFIRM / CANCEL_CONFIRM / MARK_EXPEDITED / PACK / SHIP / CUT / CANCEL_CUT
     */
    private String operateType;

    /**
     * 操作对象类型，参见 {@link ZcOrderOperateTargetTypeEnum}
     * 取值：ORDER / CURTAIN / MATERIAL
     */
    private String targetType;

    /**
     * 操作对象 ID（窗帘行 ID 或用料明细 ID；订单级操作时为 null）
     */
    private Long targetId;

    /** 操作对象的操作前状态 */
    private String beforeStatus;

    /** 操作对象的操作后状态 */
    private String afterStatus;

    /**
     * 订单联动更新后的状态（仅窗帘行/用料操作触发订单状态变化时填写，其他情况为 null）
     */
    private String orderAfterStatus;

    /**
     * 扩展信息 JSON（操作类型不同，内容不同）
     * 裁剪/撤销裁剪：{"batchNo":"B001","quantity":12.5}
     */
    private String extJson;

    /** 备注 */
    private String note;

}
