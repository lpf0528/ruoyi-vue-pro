package cn.iocoder.yudao.module.zc.controller.admin.vo.progress;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ZcProgressDefinitionPageReqVO extends PageParam {

    private String code;
    private String name;
    private Integer progressKind;

}
