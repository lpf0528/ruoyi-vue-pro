package cn.iocoder.yudao.module.zc.controller.admin.vo.progress;

import lombok.Data;

@Data
public class ZcProgressDefinitionSaveReqVO {

    private Long id;
    private String code;
    private String name;
    private Integer progressKind;
    private String phaseGroup;
    private Integer sort;
    private Boolean isMilestone;
    private Boolean allowRepeat;
    private Integer status;
    private String remark;

}
