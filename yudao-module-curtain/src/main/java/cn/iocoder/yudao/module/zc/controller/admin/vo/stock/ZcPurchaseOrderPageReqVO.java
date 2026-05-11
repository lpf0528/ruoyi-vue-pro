package cn.iocoder.yudao.module.zc.controller.admin.vo.stock;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ZcPurchaseOrderPageReqVO extends PageParam {

    private String purchaseNo;
    private Long supplierId;
    private Integer auditStatus;

}
