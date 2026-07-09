package cn.iocoder.yudao.module.zc.enums;

import cn.hutool.core.util.ObjUtil;
import lombok.Getter;

/**
 * 车间员工状态枚举
 */
@Getter
public enum ZcWorkshopUserStatusEnum {

    /** 关闭 */
    DISABLE(0, "关闭"),

    /** 开启 */
    ENABLE(1, "开启");

    /** 状态码，对应 zc_workshop_user.status */
    private final Integer status;

    /** 中文名称 */
    private final String label;

    ZcWorkshopUserStatusEnum(Integer status, String label) {
        this.status = status;
        this.label = label;
    }

    public static boolean isEnable(Integer status) {
        return ObjUtil.equal(ENABLE.status, status);
    }

    public static boolean isDisable(Integer status) {
        return ObjUtil.equal(DISABLE.status, status);
    }

}
