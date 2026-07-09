package cn.iocoder.yudao.module.zc.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 窗帘粘贴方向枚举
 *
 * <p>对应字典类型 {@code zc_paste_direction}</p>
 */
@Getter
@AllArgsConstructor
public enum ZcPasteDirectionEnum {

    /** 正反贴：正面与背面均可粘贴 */
    ZFT("正反贴"),

    /** 反贴：仅背面粘贴 */
    FT("反贴"),

    /** 正贴：仅正面粘贴 */
    ZT("正贴");

    /** 中文名称 */
    private final String label;

}
