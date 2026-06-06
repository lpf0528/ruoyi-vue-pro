package cn.iocoder.yudao.module.zc.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 库存变动操作类型枚举
 */
@Getter
@AllArgsConstructor
public enum ZcInventoryRecordOperateEnum {

    /** 盘点：手动盘点库存 */
    PANDIAN("盘点"),

    /** 入库：采购入库操作 */
    RUKU("入库"),

    /** 裁剪：生产裁剪领料出库 */
    CAIJIAN("裁剪"),

    /** 撤销裁剪：取消裁剪，库存回退 */
    CANCEL_CAIJIAN("撤销裁剪");

    /** 中文名称 */
    private final String label;

}
