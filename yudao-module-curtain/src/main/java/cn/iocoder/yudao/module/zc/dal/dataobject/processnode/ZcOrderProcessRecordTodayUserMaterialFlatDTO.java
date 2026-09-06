package cn.iocoder.yudao.module.zc.dal.dataobject.processnode;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 当日员工节点用料统计 查询结果 DTO
 *
 * @author 01Coder
 */
@Data
public class ZcOrderProcessRecordTodayUserMaterialFlatDTO {

    /**
     * 主操作人员 ID
     */
    private Long masterId;

    /**
     * 主操作人员姓名
     */
    private String masterName;

    /**
     * 工序节点 ID
     */
    private Long nodeId;

    /**
     * 工序节点名称
     */
    private String nodeName;

    /**
     * 用料组件 ID
     */
    private Long elementId;

    /**
     * 用料组件名称
     */
    private String elementName;

    /**
     * 工序次数
     */
    private Long processCount;

    /**
     * 工序用料合计
     */
    private BigDecimal totalQuantity;

}
