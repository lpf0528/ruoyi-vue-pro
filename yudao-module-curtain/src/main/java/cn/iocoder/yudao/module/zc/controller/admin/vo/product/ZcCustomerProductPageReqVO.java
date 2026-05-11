package cn.iocoder.yudao.module.zc.controller.admin.vo.product;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ZcCustomerProductPageReqVO extends PageParam {

    private Long customerId;
    private Long productId;

}
