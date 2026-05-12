package cn.iocoder.yudao.module.zc.controller.admin.vo.stock;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ZcProductBatchPageReqVO extends PageParam {

    private String batchNo;
    private Long productId;
    private Long warehouseId;
    private Long purchaseOrderId;

}
