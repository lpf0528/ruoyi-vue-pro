package cn.iocoder.yudao.module.zc.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 窗帘加工类型枚举
 *
 * <p>对应字典类型 {@code zc_process_type}</p>
 */
@Getter
@AllArgsConstructor
public enum ZcProcessTypeEnum {

    /** 定宽买高：以宽度为基准，按高度计费 */
    DKMG("定宽买高"),

    /** 定高买宽：以高度为基准，按宽度计费 */
    DGMK("定高买宽");

    /** 中文名称 */
    private final String label;

}
