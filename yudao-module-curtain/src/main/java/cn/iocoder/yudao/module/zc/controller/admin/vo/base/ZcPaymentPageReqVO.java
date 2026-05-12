package cn.iocoder.yudao.module.zc.controller.admin.vo.base;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ZcPaymentPageReqVO extends PageParam {

    private String name;

}
