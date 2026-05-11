package cn.iocoder.yudao.module.zc.controller.admin.vo.curtain;

import lombok.Data;

@Data
public class ZcCurtainTemplateSaveReqVO {

    private Long id;
    private Long curtainId;
    private Long structureId;
    private Long elementId;
    private String unitValue;

}
