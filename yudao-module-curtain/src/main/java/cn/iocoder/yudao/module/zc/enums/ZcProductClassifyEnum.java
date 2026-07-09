package cn.iocoder.yudao.module.zc.enums;

import lombok.Getter;

/**
 * 产品物料分类枚举
 *
 * <p>对应字典类型 {@code zc_product_classify}</p>
 */
@Getter
public enum ZcProductClassifyEnum {

    /** 运费：物流运费项 */
    YUNFEI("运费"),

    /** 样册：产品样本册 */
    YANGCE("样册"),

    /** 其他：未归类物料 */
    QITA("其他"),

    /** 窗帘布：主面料 */
    CHUANGLIANBU("窗帘布"),

    /** 赠品：随单赠送物品 */
    ZENGPIN("赠品"),

    /** 绑带：窗帘绑带配件 */
    BANGDAI("绑带"),

    /** 窗帘纱：纱帘面料 */
    CHUANGLIANSHA("窗帘纱"),

    /** 成品：已加工完成的成品帘 */
    CHENGPIN("成品");

    /** 中文名称 */
    private final String label;

    ZcProductClassifyEnum(String label) {
        this.label = label;
    }

}
