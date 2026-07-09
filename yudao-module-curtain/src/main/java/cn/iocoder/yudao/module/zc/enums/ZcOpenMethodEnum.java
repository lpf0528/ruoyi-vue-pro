package cn.iocoder.yudao.module.zc.enums;

import lombok.Getter;

/**
 * 窗帘打开方式枚举
 *
 * <p>对应字典类型 {@code zc_open_method}</p>
 */
@Getter
public enum ZcOpenMethodEnum {

    /** 右开：窗帘向右侧拉开 */
    RIGHT_OPEN("右开"),

    /** 左开：窗帘向左侧拉开 */
    LEFT_OPEN("左开"),

    /** 四开：两侧各两幅，共四幅拉开 */
    FOUR_OPEN("四开"),

    /** 三开：三幅窗帘拉开 */
    THREE_OPEN("三开"),

    /** 双开：两幅从中间向两侧拉开 */
    TWO_OPEN("双开"),

    /** 单开：单幅窗帘拉开 */
    ONE_OPEN("单开");

    /** 中文名称 */
    private final String label;

    ZcOpenMethodEnum(String label) {
        this.label = label;
    }

}
