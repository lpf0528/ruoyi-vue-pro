package cn.iocoder.yudao.module.zc.controller.admin.vo.progress;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class ZcOrderProgressLogPageReqVO extends PageParam {

    @NotNull
    private Long orderId;

}
