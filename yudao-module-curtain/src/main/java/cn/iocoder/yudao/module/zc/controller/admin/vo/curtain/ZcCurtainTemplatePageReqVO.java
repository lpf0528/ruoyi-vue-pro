package cn.iocoder.yudao.module.zc.controller.admin.vo.curtain;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ZcCurtainTemplatePageReqVO extends PageParam {

    private Long curtainId;
    private Long structureId;

}
