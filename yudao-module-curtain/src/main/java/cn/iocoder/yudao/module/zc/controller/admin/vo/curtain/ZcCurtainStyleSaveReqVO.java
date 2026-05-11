package cn.iocoder.yudao.module.zc.controller.admin.vo.curtain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ZcCurtainStyleSaveReqVO {

    private Long id;
    private String name;
    private Long seriesId;
    private String pasteDirection;
    private String openMethod;
    private Long installProcessId;
    private String processType;
    private BigDecimal pleatRatioValue;
    private BigDecimal pleatsDistance;
    private String note;

}
