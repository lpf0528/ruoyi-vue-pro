package cn.iocoder.yudao.module.zc.enums;

import cn.iocoder.yudao.framework.dict.core.DictFrameworkUtils;
import lombok.Getter;

/**
 * 系统内置工序节点枚举
 *
 * <p>对应字典类型 {@link #DICT_TYPE}，用于查询 group=0 的系统工序节点及 nodeName 兜底展示。</p>
 */
@Getter
public enum ZcSystemProcessNodeEnum {

    /** 配料：裁剪出库后自动记录的工序 */
    PEILIAO("配料", 10),
    /** 打包：窗帘行打包完成 */
    PACK("打包", 80),
    /** 发货：窗帘行发货完成 */
    SHIP("发货", 90);

    /** 字典类型 */
    public static final String DICT_TYPE = "zc_system_process_node";

    /** 默认中文名称（字典未配置时的兜底） */
    private final String label;

    /** 排序号，数字越小越靠前 */
    private final Integer sort;

    ZcSystemProcessNodeEnum(String label, Integer sort) {
        this.label = label;
        this.sort = sort;
    }

    /**
     * 从字典读取工序名称，字典未配置时回退枚举内置 label
     *
     * @return 工序名称
     */
    public String getDictLabel() {
        String dictLabel = DictFrameworkUtils.parseDictDataLabel(DICT_TYPE, name());
        return dictLabel != null ? dictLabel : label;
    }

}
