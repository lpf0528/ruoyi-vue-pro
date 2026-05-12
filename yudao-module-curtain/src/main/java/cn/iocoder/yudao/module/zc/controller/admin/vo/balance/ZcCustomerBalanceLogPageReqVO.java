package cn.iocoder.yudao.module.zc.controller.admin.vo.balance;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ZcCustomerBalanceLogPageReqVO extends PageParam {

    private Long customerId;
    private String bizType;

}
