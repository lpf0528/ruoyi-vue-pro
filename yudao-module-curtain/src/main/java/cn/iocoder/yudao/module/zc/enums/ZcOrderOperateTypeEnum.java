package cn.iocoder.yudao.module.zc.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 销售订单操作类型枚举
 *
 * <p>对应 {@code zc_order_operation_log.operate_type} 字段</p>
 */
@Getter
@AllArgsConstructor
public enum ZcOrderOperateTypeEnum {

    CONFIRM("确认订单"),
    CANCEL_CONFIRM("取消确认"),
    MARK_EXPEDITED("标记加急"),
    CANCEL_EXPEDITED("取消加急"),
    PACK("打包"),
    CANCEL_PACK("撤销打包"),
    SHIP("发货"),
    CANCEL_SHIP("撤销发货"),
    CUT("裁剪配料"),
    CANCEL_CUT("撤销裁剪");

    /** 中文名称 */
    private final String label;

}
